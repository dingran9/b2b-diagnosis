package com.eeduspace.test;

import com.eeduspace.b2b.report.model.StudentAnswerResultDto;
import com.eeduspace.b2b.report.model.StudentQuestionAnswerResultDto;
import com.eeduspace.b2b.report.model.StudentReportDto;
import com.eeduspace.b2b.report.model.question.KnowledgeModel;
import com.eeduspace.b2b.report.model.question.Productionmodels;
import com.eeduspace.b2b.report.model.question.Sons;
import com.eeduspace.b2b.report.service.B2BReportOpenService;
import com.eeduspace.report.B2bReportApplication;
import com.eeduspace.report.third.client.model.ResultResponse;
import com.eeduspace.report.util.GsonUtils;
import com.eeduspace.test.model.*;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 学生报告测试
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-03-27 10:47
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = B2bReportApplication.class,webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class StudentReportTest {
    @Autowired
    private B2BReportOpenService b2BReportOpenService;
    @Test
    public void genStudentReport(){
         int i=1;
         int wrong=0;
         int right=0;
         RestTemplate restTemplate=new RestTemplate();
         List<StudentQuestionAnswerResultDto> resultDtos=Lists.newArrayList();
        ResultResponse forObject = restTemplate.getForObject("http://211.157.179.196:9090/llsfw/paperController/getPaperByscore3/8a2a74685acc4b52015b0de990f0391c", ResultResponse.class);
        PaperSystem paperModel= GsonUtils.toEntity(GsonUtils.toJson(forObject.getDatas()),PaperSystem.class);
        for (QuestionSet questionSet : paperModel.getPaperSystemQusetionType()) {
             for (BigQuestionSystem bigQuestionSystem : questionSet.getTypeList()) {
                 for (SmallQuestionSystem smallQuestionSystem : bigQuestionSystem.getList()) {
                     List<KnowledgeModel> knowledgeModels= Lists.newArrayList();
                     List<Productionmodels> productionmodelsList= Lists.newArrayList();
                     String qsn=i+"";
                     StudentQuestionAnswerResultDto dto=new StudentQuestionAnswerResultDto();
                     if(i%2==0){
                         dto.setAnswerResult(0);
                         wrong++;
                     }else{
                         dto.setAnswerResult(1);
                         right++;
                     }
                     dto.setScore(smallQuestionSystem.getScore());
                     dto.setIsComplex(0);
                     dto.setQuestionSn(qsn);
                     dto.setQuestionCode(smallQuestionSystem.getId());
                     for (SimpleTree simpleTree : smallQuestionSystem.getTree()) {
                         KnowledgeModel knowledgeModel=new KnowledgeModel();
                         knowledgeModel.setIsTrue(dto.getAnswerResult());
                         knowledgeModel.setKnowledgeName(simpleTree.getName());
                         knowledgeModel.setKnowledgeCode(simpleTree.getId());
                         knowledgeModels.add(knowledgeModel);
                     }
                     for (BaseProductionModel baseProductionModel : smallQuestionSystem.getBasetree()) {
                         Productionmodels productionmodels=new Productionmodels();
                         productionmodels.setBasecodeVo(baseProductionModel.getBasecodeVo());
                         productionmodels.setBasenameVo(baseProductionModel.getBasenameVo());
                         productionmodels.setIsTrue(dto.getAnswerResult());
                         productionmodels.setTypecodeVo(baseProductionModel.getTypecodeVo());
                         productionmodels.setSons(GsonUtils.toList(GsonUtils.toJson(baseProductionModel.getSons()), Sons.class));
                         productionmodelsList.add(productionmodels);
                     }
                     dto.setKnowledgeModelList(knowledgeModels);
                     dto.setProductionmodelsList(productionmodelsList);
                     i++;
                     resultDtos.add(dto);
                 }
             }
         }

         StudentAnswerResultDto studentAnswerResultDto=new StudentAnswerResultDto();
         studentAnswerResultDto.setGradeCode(paperModel.getGradeCode());
         studentAnswerResultDto.setSubjectCode(paperModel.getSubjectCode());
         studentAnswerResultDto.setBookTypeVersionCode(paperModel.getBooktype());
         studentAnswerResultDto.setPaperCode(paperModel.getId());
         studentAnswerResultDto.setMakePaperTime(new Timestamp(new Date().getTime()));
         studentAnswerResultDto.setMarkPaperRecordCode("100012");
         studentAnswerResultDto.setPaperName(paperModel.getPaperName());
         studentAnswerResultDto.setPaperStandardScore(80d);
         studentAnswerResultDto.setPaperScore(paperModel.getTotalScore()+"");
         studentAnswerResultDto.setReleaseCode("000001");
         studentAnswerResultDto.setReleaseName("第一次发布考试记录");
         studentAnswerResultDto.setRightCount(right);
         studentAnswerResultDto.setScore(85d);
         studentAnswerResultDto.setUseTime(150);
         studentAnswerResultDto.setWrongCount(wrong);
         studentAnswerResultDto.setClassCode("01");
         studentAnswerResultDto.setClassName("1班");
         studentAnswerResultDto.setUserCode("9002");
         studentAnswerResultDto.setUserName("董大");
         studentAnswerResultDto.setTeacherCode("t_001");
        // studentAnswerResultDto.setTeacherName("董大教授");
         studentAnswerResultDto.setSchoolCode("sc_0001");
         studentAnswerResultDto.setSchoolName("学校名称");
         studentAnswerResultDto.setStudentQuestionAnswerResultDtos(resultDtos);

        StudentAnswerResultDto sss=new StudentAnswerResultDto();
        sss.setUserCode("111");
        StudentReportDto studentReportDto = b2BReportOpenService.generateStudentReport(sss);
        log.info("studentReportDto---->"+ GsonUtils.toJson(studentReportDto));

     }

}
