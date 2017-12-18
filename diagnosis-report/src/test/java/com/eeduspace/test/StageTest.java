package com.eeduspace.test;

import com.eeduspace.b2b.report.constant.ReportConstant;
import com.eeduspace.b2b.report.model.AchievementDto;
import com.eeduspace.b2b.report.model.BaseAbilityDto;
import com.eeduspace.b2b.report.model.BaseKnowledgeModuleDto;
import com.eeduspace.b2b.report.service.StageTestReportOpenService;
import com.eeduspace.report.B2bReportApplication;
import com.eeduspace.report.util.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-10-12 14:57
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = B2bReportApplication.class,webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class StageTest {
    @Autowired
    private StageTestReportOpenService stageTestReportOpenService;
    @Test
    public void getStageAbility() throws Exception{
        String releaseCode = "DD80122B5D9846A8901C365DAF513509";
        Integer districtId = 0;
        String semester = "20181";
        String subjectCode = "2";
        Integer artType = 2;

        List<BaseAbilityDto> stageAbility = stageTestReportOpenService.getStageAbility(ReportConstant.TEACHER_CONTRAST, releaseCode, semester, null,subjectCode);
        System.out.println(GsonUtils.toJson(stageAbility));
    }

    @Test
    public void getStageKnowledgeModule() throws Exception{
        String releaseCode = "DD80122B5D9846A8901C365DAF513509";
        Integer districtId = 0;
        String semester = "20181";
        String subjectCode = "2";
        Integer artType = 2;
        List<BaseKnowledgeModuleDto> stageKnowledgeModule = stageTestReportOpenService.getStageKnowledgeModule(ReportConstant.TEACHER_CONTRAST, releaseCode, semester, null, subjectCode);
        System.out.println(GsonUtils.toJson(stageKnowledgeModule));
    }

    @Test
    public void getStageTeachineAchievement() throws Exception{
        String releaseCode = "DD80122B5D9846A8901C365DAF513509";
        Integer districtId = 0;
        String semester = "20181";
        String subjectCode = "2";
        Integer artType = 2;
        List<AchievementDto> stageTeachineAchievement = stageTestReportOpenService.getStageTeachineAchievement(ReportConstant.TEACHER_CONTRAST, releaseCode, null, semester, subjectCode);
        System.out.println(GsonUtils.toJson(stageTeachineAchievement));
    }

    @Test
    public void getStandardScoreChange() throws Exception{
        String releaseCode = "DD80122B5D9846A8901C365DAF513509";
        Integer districtId = 430103;
        String semester = "20181";
        Integer subjectCode = 2;
        Integer artType = 2;
        Integer gradeCode = 22;
        Map<String, Object> standardScoreChange = stageTestReportOpenService.getStandardScoreChange(ReportConstant.TEACHER_CONTRAST, releaseCode, districtId, semester, subjectCode, gradeCode);
        System.out.println(GsonUtils.toJson(standardScoreChange));
    }


}
