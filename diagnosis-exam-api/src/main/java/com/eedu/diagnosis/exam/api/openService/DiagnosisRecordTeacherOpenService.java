package com.eedu.diagnosis.exam.api.openService;

import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.exam.api.dto.DiagnosisRecordTeacherDto;
import com.eedu.diagnosis.exam.api.dto.ReleaseDiagnosisDto;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by dqy on 2017/3/16.
 */
public interface DiagnosisRecordTeacherOpenService {
    /**
     * 根据记录code获取单体
     * @param teacherRecordCode
     * @return
     */
    DiagnosisRecordTeacherDto getDiagnosisRecordTeacherByCode(String teacherRecordCode) throws Exception;
    /**
     * 教师发布诊断
     * @return
     */
    String release(DiagnosisRecordTeacherDto drtd) throws Exception;

    /**
     * 教师获取诊断记录列表 可排序
     * @param drtd
     * @param pageSize
     * @param pageNum
     * @param order 排序条件
     * @return
     * @throws Exception
     */
    PageInfo<DiagnosisRecordTeacherDto> getDiagnosisList(DiagnosisRecordTeacherDto drtd, Integer pageNum, Integer pageSize, Order order) throws Exception;

    /**
     * 按条件获取
     * @param drtd
     * @param order
     * @return
     * @throws Exception
     */
    List<DiagnosisRecordTeacherDto> getAll(DiagnosisRecordTeacherDto drtd,Order order) throws Exception;

    /**
     * 更新教师诊断记录状态
     * @param dto
     * @return
     */
    boolean update(DiagnosisRecordTeacherDto dto) throws Exception;

    /**
     * 教师获取测试记录列表 附带已经出报告的班级数量
     * @param dto
     * @param pageNum
     * @param pageSize
     * @param order
     * @return
     */
    PageInfo<DiagnosisRecordTeacherDto> getDiagnosisListWithReportCount(DiagnosisRecordTeacherDto dto, Integer pageNum, Integer pageSize, Order order) throws Exception;

    /**
     * 教师和校长获取全科考试列表  新来的教师无法看到之前考试记录
     * @param dto
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<DiagnosisRecordTeacherDto> getDiagnosisListForMaster(DiagnosisRecordTeacherDto dto, Integer pageNum, Integer pageSize) throws Exception;

    List<Map<String,Object>> getTeachingProgressByClasses(Integer gradeCode,List<String> unitCodes, Integer districtId, String semester) throws Exception;

    String teachingManagerRelease(ReleaseDiagnosisDto releaseExamDto) throws Exception;

    PageInfo<DiagnosisRecordTeacherDto> getDiagnosisListBySubject(DiagnosisRecordTeacherDto drtd, Integer pageNum, Integer pageSize, Order order) throws Exception;
}
