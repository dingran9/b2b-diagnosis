package com.eedu.diagnosis.manager.service.taskService.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.eedu.diagnosis.exam.api.dto.DiagnosisClassRelationDto;
import com.eedu.diagnosis.exam.api.dto.DiagnosisRecordStudentDto;
import com.eedu.diagnosis.exam.api.dto.DiagnosisRecordTeacherDto;
import com.eedu.diagnosis.exam.api.enumeration.DiagnosisStausEnum;
import com.eedu.diagnosis.exam.api.openService.DiagnosisClassRelationOpenService;
import com.eedu.diagnosis.exam.api.openService.DiagnosisRecordStudentOpenService;
import com.eedu.diagnosis.exam.api.openService.DiagnosisRecordTeacherOpenService;
import com.eedu.diagnosis.manager.service.taskService.MakeReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dqy on 2017/3/20.
 */
@Component
public class MakeReportServiceImpl implements MakeReportService {
    private final Logger logger = LoggerFactory.getLogger(MakeReportService.class);
    public static final long DURATION = 15;//15天之前的数据将不再处理
    @Autowired
    private DiagnosisRecordTeacherOpenService diagnosisRecordTeacherOpenService;
    @Autowired
    private DiagnosisClassRelationOpenService diagnosisClassRelationOpenService;
    @Autowired
    private DiagnosisRecordStudentOpenService diagnosisRecordStudentOpenService;

    //每分钟执行一次
    @Scheduled(cron = "0 0/1 * * * ?")
    @Override
    public boolean makeReprot() {
        List<DiagnosisRecordTeacherDto> list = new ArrayList<>();
        try {
            DiagnosisRecordTeacherDto dto = new DiagnosisRecordTeacherDto();
            dto.setIsSnapshot(0);//未生成报告
            list = diagnosisRecordTeacherOpenService.getAll(dto, null);
        } catch (Exception e) {
            logger.info("MakeReportServiceImpl error :" + e.getMessage());
        }
        if (!list.isEmpty()) {
            list.forEach(diagnosisRecordTeacherDto -> {
                Date endTime = diagnosisRecordTeacherDto.getEndTime();
                LocalDateTime examEndTime = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                Duration between = Duration.between(examEndTime, LocalDateTime.now());
                if (between.toMinutes() > 0 && between.toDays() < DURATION) {
                    try {
                        //更新班级报告状态
                        DiagnosisClassRelationDto dcdto = new DiagnosisClassRelationDto();
                        dcdto.setDiagnosisRecordCode(diagnosisRecordTeacherDto.getCode());
                        dcdto.setHasReport(0);
                        //未生成报告的集合
                        List<DiagnosisClassRelationDto> notReportList = diagnosisClassRelationOpenService.getListByParam(dcdto);
                        for (DiagnosisClassRelationDto dcr : notReportList) {
                            DiagnosisRecordStudentDto drsdto = new DiagnosisRecordStudentDto();
                            drsdto.setDiagnosisTeacherRecordCode(diagnosisRecordTeacherDto.getCode());
                            drsdto.setClassCode(dcr.getClassCode());
                            drsdto.setSubjectCode(dcr.getSubjectCode());
                            List<DiagnosisRecordStudentDto> totalRecords = diagnosisRecordStudentOpenService.getAll(drsdto, null);
                            if (!CollectionUtils.isEmpty(totalRecords)) {
                                int notJoinSize = 0;
                                int hasReportSize = 0;
                                for (DiagnosisRecordStudentDto diagnosisRecordStudentDto1 : totalRecords) {
                                    if (DiagnosisStausEnum.RELEASE.getValue().equals(diagnosisRecordStudentDto1.getDiagnosisStatus())) {
                                        notJoinSize++;
                                    } else if (DiagnosisStausEnum.REPROT.getValue().equals(diagnosisRecordStudentDto1.getDiagnosisStatus())) {
                                        hasReportSize++;
                                    }
                                }
                                //如果未参加考试的数量和已出报告的数量之和等于发布的考试数量 设置教师端的报告状态为已出报告
                                if ((totalRecords.size() - notJoinSize == hasReportSize) && totalRecords.size() != notJoinSize) {
                                    DiagnosisClassRelationDto dcrdto = new DiagnosisClassRelationDto();
                                    dcrdto.setCode(dcr.getCode());
                                    dcrdto.setHasReport(1);
                                    dcrdto.setIsRead(0);
                                    diagnosisClassRelationOpenService.update(dcrdto);
                                }
                            }
                        }
                    } catch (Exception e) {
                        logger.error("MakeReportService update error" + e.getMessage());
                    }
                    try {
                        DiagnosisRecordTeacherDto drdto = new DiagnosisRecordTeacherDto();
                        drdto.setCode(diagnosisRecordTeacherDto.getCode());
                        drdto.setIsSnapshot(1);
                        drdto.setUpdateTime(new Date());
                        diagnosisRecordTeacherOpenService.update(drdto);
                    } catch (Exception e) {
                        logger.error("MakeReportService update error" + e.getMessage());
                    }
                }
            });
        }
        return true;
    }
}
