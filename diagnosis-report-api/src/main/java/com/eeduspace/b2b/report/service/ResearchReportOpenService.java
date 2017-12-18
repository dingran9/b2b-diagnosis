package com.eeduspace.b2b.report.service;

import com.eeduspace.b2b.report.model.AchievementDto;
import com.eeduspace.b2b.report.model.BaseAbilityDto;
import com.eeduspace.b2b.report.model.BaseKnowledgeModuleDto;
import com.eeduspace.b2b.report.model.report.*;

import java.util.List;
import java.util.Map;

/**
 * Created by liuhongfei on 2017/9/28.
 */
public interface ResearchReportOpenService {

    /**
     * 获取单元教学成绩
     * @param contrastType  对比类型
     * @param unitCode 单元code
     * @param semester 学期
     * @param areaCode 区域code
     * @return
     * @throws Exception
     */
    public List<AchievementDto> getUnitTeachingAchievement(String contrastType, String unitCode, String semester, Integer areaCode) throws Exception;
    /**
     * 获取单元能力信息
     * @param contrastType 对比类型
     * @param unitCode 单元code
     * @param semester 学期
     * @param areaCode 区域code
     * @return
     * @throws Exception
     */
    public List<BaseAbilityDto> getUnitAbility(String contrastType, String unitCode, String semester, Integer areaCode) throws Exception;

    /**
     * 获取单元知识点模块掌握情况
     * @param contrastType 对比类型
     * @param unitCode 单元code
     * @param semester 学期
     * @param areaCode 区域code
     * @return
     * @throws Exception
     */
    public List<BaseKnowledgeModuleDto> getUnitKnowledgeModule(String contrastType, String unitCode, String semester, Integer areaCode) throws Exception;
    /**
     * 获取区域年级下单元平均分
     * @param gradeCode 年级code
     * @param semester  学期code
     * @param unitModels  单元code集合
     * @param areaCode  区域code
     * @param contrastType 对比类型
     * @return
     */
    public Map<String,Object> getAreaUnitScore(String contrastType,String gradeCode, String semester, List<UnitModel> unitModels, Integer areaCode) throws Exception;
    /**
     * 教研员单元测试题质量分析
     * @param unitCode  单元CODE
     * @param districtId  区县ID
     * @param semester  学期
     * @param paperCode 试卷CODE
     * @throws Exception
     */
    public List<QuestionsqualityModel> unitQualityofQuestionsReport(String unitCode, Integer districtId, String semester, String paperCode) throws Exception;
    /**
     * 教研员单元全区测情况
     * @param unitCode 单元CODE
     * @param districtId  区县ID
     * @param semester 学期
     * @throws Exception
     */
    public AreaHappenModel unitAreaHappening(String unitCode, Integer districtId, String semester) throws Exception;
    /**
     *  教研员单元区域，学校，班级 学生成绩分布
     * @param unitCode  单元CODE
     * @param districtId 区县ID
     * @param semester  学期
     * @param schoolCode  学校CODE
     * @param gradeCode 年级CODE
     * @param classCode 班级CODE
     * @return
     * @throws Exception
     */
    public List<DistributedModel> unitResultsDistribution(String unitCode, Integer districtId, String semester, Integer schoolCode, Integer gradeCode, Integer classCode)throws Exception;



    /**
     * 教研员单元全区各班级测试情况
     * @param unitCode 单元CODE
     * @param districtId  区县ID
     * @param semester 学期
     * @throws Exception
     */
    public List<ClassHappenModel> unitAreaHappeningByclass(String unitCode, Integer districtId, String semester) throws Exception;

    /**
     * 全区各年级总体情进度
     * @param gradeCode 年级CODE
     * @param unitModels 多单元CODE
     * @param districtId 区县CODE
     * @param semester 学期
     * @return
     * @throws Exception
     */
    public  List<TeachingProgressModel> teachingProgressByArea(Integer gradeCode, List<UnitModel> unitModels, Integer districtId, String semester) throws Exception;


    /**
     * 全区各学校年级总体情进度
     * @param gradeCode 年级CODE
     * @param unitModels 多单元CODE
     * @param districtId 区县CODE
     * @param semester 学期
     * @return
     * @throws Exception
     */
    public List<SchoolProgressModel> teachingProgressBySchool(Integer gradeCode,List<UnitModel> unitModels, Integer districtId, String semester)throws Exception;


    /**
     * 全区各学校教师总体情进度
     * @param gradeCode 年级CODE
     * @param unitModels 多单元CODE
     * @param districtId 区县CODE
     * @param semester 学期
     * @return
     * @throws Exception
     */
    public List<SchoolProgressModel> teachingProgressByTeacher(Integer gradeCode,List<UnitModel> unitModels, Integer districtId, String semester) throws Exception;

    /**
     * 教研员单元全区各学校测试情况
     * @param unitCode 单元CODE
     * @param districtId  区县ID
     * @param semester 学期
     * @throws Exception
     */
    public List<SchoolHappenModel> unitAreaHappeningBySchool(String unitCode, Integer districtId, String semester) throws Exception;

    /**
     * 全区各学校教师总体情进度
     * @param gradeCode 年级CODE
     * @param unitModels 多单元CODE
     * @param districtId 区县CODE
     * @param semester 学期
     * @return
     * @throws Exception
     */
    public  List<SchoolProgressModel> teachingProgressByClass(Integer gradeCode,List<UnitModel> unitModels, Integer districtId, String semester) throws Exception;

    /**
     *  教研员单元测区域学校成绩分布
     * @param districtId 区县ID
     * @param semester  学期
     * @param gradeCode 年级CODE
     * @return
     * @throws Exception
     */
    public List<ResultsModel> unitResultsDistributionBySchool(String unitCode, Integer districtId, String semester,Integer gradeCode)throws Exception;

    /**
     *  教研员单元测区域学校班级成绩分布
     * @param districtId 区县ID
     * @param semester  学期
     * @param gradeCode 年级CODE
     * @return
     * @throws Exception
     */
    public List<ResultsModel> unitResultsDistributionByClass(String unitCode, Integer districtId, String semester,Integer gradeCode)throws Exception;


}
