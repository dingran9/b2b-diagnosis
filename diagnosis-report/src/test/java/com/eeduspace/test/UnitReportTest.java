package com.eeduspace.test;

import com.eeduspace.b2b.report.constant.ReportConstant;
import com.eeduspace.b2b.report.model.AchievementDto;
import com.eeduspace.b2b.report.model.BaseAbilityDto;
import com.eeduspace.b2b.report.model.BaseKnowledgeModuleDto;
import com.eeduspace.b2b.report.model.report.UnitModel;
import com.eeduspace.b2b.report.service.ResearchReportOpenService;
import com.eeduspace.report.B2bReportApplication;
import com.eeduspace.report.util.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 单元报告测试类
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-10-13 10:36
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = B2bReportApplication.class,webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UnitReportTest {
    @Autowired
    private ResearchReportOpenService researchReportOpenService;
    @Test
    public void getUnitTeachingAchievement() throws Exception{
        String unitCode = "7e582226df1b4c99a24331a2ef1dda0c";
        Integer districtId = 430103;
        String semester = "20181";
        String paperCode = "21AB4BAF8EA24F2293ECB27F339C61F8";
        //只能查 教师与班级
        List<AchievementDto> unitTeachingAchievement = researchReportOpenService.getUnitTeachingAchievement(ReportConstant.TEACHER_CONTRAST, unitCode, semester, districtId);
        System.out.println(GsonUtils.toJson(unitTeachingAchievement));
    }
    @Test
    public void getUnitAbility() throws Exception{
        String unitCode = "7e582226df1b4c99a24331a2ef1dda0c";
        Integer districtId = 430103;
        String semester = "20181";
        String paperCode = "21AB4BAF8EA24F2293ECB27F339C61F8";
        List<BaseAbilityDto> unitAbility = researchReportOpenService.getUnitAbility(ReportConstant.TEACHER_CONTRAST, unitCode, semester, districtId);
        System.out.println(GsonUtils.toJson(unitAbility));
    }
    @Test
    public void getUnitKnowledgeModule() throws Exception{
        String unitCode = "7e582226df1b4c99a24331a2ef1dda0c";
        Integer districtId = 430103;
        String semester = "20181";
        String paperCode = "21AB4BAF8EA24F2293ECB27F339C61F8";
        List<BaseKnowledgeModuleDto> unitKnowledgeModule = researchReportOpenService.getUnitKnowledgeModule(ReportConstant.TEACHER_CONTRAST, unitCode, semester, districtId);
        System.out.println(GsonUtils.toJson(unitKnowledgeModule));
    }
    @Test
    public void getAreaUnitScore() throws Exception{
        String unitCode = "7e582226df1b4c99a24331a2ef1dda0c";
        Integer districtId = 430103;
        String semester = "20181";
        String paperCode = "21AB4BAF8EA24F2293ECB27F339C61F8";
        String gradeCode="22";
        List<UnitModel> unitModels = new ArrayList<>();
        UnitModel unitModel = new UnitModel();
        unitModel.setUnitName("第一单元");
        unitModel.setUnitCode(unitCode);
        unitModel.setSort(1);
        unitModels.add(unitModel);
        //只有年级与学校
        Map<String, Object> areaUnitScore = researchReportOpenService.getAreaUnitScore(ReportConstant.SCHOOL_CONTRAST, gradeCode, semester, unitModels, districtId);
        System.out.println(GsonUtils.toJson(areaUnitScore));
    }

}
