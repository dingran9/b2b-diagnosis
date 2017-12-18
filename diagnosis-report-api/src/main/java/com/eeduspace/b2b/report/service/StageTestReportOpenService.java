package com.eeduspace.b2b.report.service;

import com.eeduspace.b2b.report.model.AchievementDto;
import com.eeduspace.b2b.report.model.BaseAbilityDto;
import com.eeduspace.b2b.report.model.BaseKnowledgeModuleDto;
import com.eeduspace.b2b.report.model.report.*;
import com.eeduspace.b2b.report.model.report.principal.StageTeachingProgressModel;

import java.util.List;
import java.util.Map;

/**
 * Created by liuhongfei on 2017/10/10.
 */
public interface StageTestReportOpenService {
    /**
     * 获取阶段考试能力信息
     * @param contrastType 对比类型
     * @param releaseCode 发布记录code
     * @param semester 学期
     * @param areaCode 区域code
     * @param subjectCode 学科code
     * @return
     * @throws Exception
     */
    public List<BaseAbilityDto> getStageAbility(String contrastType, String releaseCode, String semester, Integer areaCode,String subjectCode) throws Exception;

    /**
     * 获取阶段考试知识点模块掌握情况
     * @param contrastType 对比类型
     * @param releaseCode 发布记录code
     * @param semester 学期
     * @param areaCode 区域code
     * @param subjectCode 学科code
     * @return
     * @throws Exception
     */
    public List<BaseKnowledgeModuleDto> getStageKnowledgeModule(String contrastType, String releaseCode, String semester, Integer areaCode,String subjectCode) throws Exception;
    /**
     * 获取标准分平均分变动信息
     * @param contrastType 对比类型
     * @param releaseCode 发布记录code
     * @param areaCode 区域code
     * @param semester 学期code
     * @param gradeCode  学年
     * @param subjectCode 学科
     * @return
     * @throws Exception
     */
    public Map<String,Object> getStandardScoreChange(String contrastType,String releaseCode,Integer areaCode,String semester,Integer subjectCode,Integer gradeCode) throws Exception;
    /**
     * 获取阶段考试教学成绩
     * @param contrastType 对比类型
     * @param releaseCode  发布记录code
     * @param areaCode     区域code
     * @param semester     学期
     * @param subjectCode 学期code
     * @return
     * @throws Exception
     */
    public List<AchievementDto> getStageTeachineAchievement(String contrastType,String releaseCode,Integer areaCode,String semester,String subjectCode) throws Exception;
    /**
     * 教研员单元测试题质量分析
     * 教研员单元测试题质量分析
     * @param releaseCode 阶段考发布CODE
     * @param districtId  区县ID
     * @param semester  学期
     * @param artType 文理类型
     * @throws Exception
     */
    public List<QuestionsqualityModel> stageQualityofQuestionsReport(String releaseCode, Integer districtId, String semester, Integer subjectCode, Integer artType) throws Exception;


    /**
     * 教研员阶段考全区测情况
     * @param releaseCode 阶段考发布CODE
     * @param subjectCode  区县ID
     * @throws Exception
     */
    public AreaHappenModel stageAreaHappening(String releaseCode, Integer subjectCode) throws Exception;


    /**
     * 教研员阶段考全区各学校测试情况
     * @param releaseCode 单元CODE
     * @param subjectCode  区县ID
     * @throws Exception
     */
    public List<SchoolHappenModel> stageAreaHappeningBySchool(String releaseCode, Integer subjectCode) throws Exception;

    /**
     * 教研员阶段考全区各学校班级测试情况
     * @param releaseCode 单元CODE
     * @param subjectCode  区县ID
     * @throws Exception
     */
    public List<ClassHappenModel> stageAreaHappeningByclass(String releaseCode, Integer subjectCode) throws Exception;


    /**
     *  教研员阶段考区域学生成绩分布
     * @param releaseCode  阶段考发布CODE
     * @param districtId 区县ID
     * @param semester  学期
     * @param gradeCode 年级CODE
     * @param subjectCode 班级CODE
     * @return
     * @throws Exception
     */
    public List<DistributedModel> stageResultsDistribution(String releaseCode, Integer districtId, String semester, Integer gradeCode, Integer subjectCode)throws Exception;

    /**
     *  教研员阶段考区域学校成绩分布
     * @param releaseCode  阶段考发布CODE
     * @param districtId 区县ID
     * @param semester  学期
     * @param gradeCode 年级CODE
     * @return
     * @throws Exception
     */
    public List<ResultsModel> stageResultsDistributionBySchool(String releaseCode, Integer districtId, String semester,Integer gradeCode,Integer subjectCode)throws Exception;


    /**
     *  教研员阶段考区域学校班级成绩分布
     * @param releaseCode  阶段考发布CODE
     * @param districtId 区县ID
     * @param semester  学期
     * @param gradeCode 年级CODE
     * @return
     * @throws Exception
     */
    public List<ResultsModel> stageResultsDistributionByClass(String releaseCode, Integer districtId, String semester,Integer gradeCode,Integer subjectCode)throws Exception;


    /**
     * 教研员阶段考全区各年级总体情进度
     * @param gradeCode 年级CODE
     * @param districtId 区县CODE
     * @param semester 学期
     * @return
     * @throws Exception
     */
    public List<StageTeachingProgressModel> stageTeachingProgressByArea(String releaseCode, Integer districtId, String semester, Integer gradeCode, Integer subjectCode) throws Exception;

    }
