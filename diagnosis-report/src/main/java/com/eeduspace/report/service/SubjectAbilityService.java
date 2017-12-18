package com.eeduspace.report.service;

import com.eeduspace.report.model.SubjectAbilityModel;
import com.eeduspace.report.po.SubjectAbilityPo;

import java.util.List;

/**
 * <p>描述</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/17 9:31
 **/
public interface SubjectAbilityService {
    SubjectAbilityPo save(SubjectAbilityPo subjectAbilityPo);

    List<SubjectAbilityPo> batchSave(List<SubjectAbilityPo> subjectAbilityPoList);

    /**
     * 获取学习能力
     * @param releaseExamCode 发布考试记录code
     * @param teacherCode  教师code
     * @param subjectCode  学科code
     * @param examTypes 考试类型（查询的不包含的）
     * @param semester 学期
     * @param schoolCode 学校code
     * @param  gradeCode 学年code
     * @return
     */
    List<SubjectAbilityModel> getByReleaseExamCodeAndTeacherCodeAndSubjectCode(String releaseExamCode,String teacherCode,String subjectCode,List<String> examTypes,String semester,String schoolCode,String gradeCode) throws Exception;


    /**
     * 获取学习能力
     * @param releaseExamCode 发布考试记录code
     * @param subjectCode 学科code
     * @return
     * @throws Exception
     */
    List<SubjectAbilityModel> getByReleaseExamCodeAndSubjectCode(String releaseExamCode,String subjectCode) throws Exception;

    /**
     * 获取学科能力水平信息
     * @param releaseExamCode 考试发布记录code
     * @param subjectCode 学科code
     * @param examTypes 考试类型（查询不包含的）
     *                  @param semester 学期
     * @return
     * @throws Exception
     */
    List<SubjectAbilityModel> getByReleaseExamCodeAndSubjectCodeAndExamTypeAndSemester(String releaseExamCode,String subjectCode,List<String> examTypes,String semester) throws Exception;

    /**
     * 获取能力信息根据
     * @param subjectCode 学科code
     * @param examTypes  考试类型
     * @param semester    学期
     *                    @param teacherCode 教师code
     * @return
     * @throws Exception
     */
    List<SubjectAbilityModel> getBySubjectCodeAndExamTypeAndSemesterAndTeacherCode(String subjectCode,List<String> examTypes,String semester,String teacherCode) throws Exception;


    /**
     * 获取能力信息根据
     * @param schoolCode 学校code
     * @param gradeCode 学年code
     * @param subjectCode 学科code
     * @param examTypes 考试类型
     * @param semester 学期
     * @return
     * @throws Exception
     */
    List<SubjectAbilityModel> getBySchoolCodeAndGradeCodeAndSubjectCodeAndExamTypeAndSemester(String schoolCode,String gradeCode,String subjectCode,List<String> examTypes,String semester) throws Exception;

    /**
     * 根据发布记录code获取 能力信息
     * @param releaseCodes 发布记录code
     * @return
     * @throws Exception
     */
    List<SubjectAbilityModel> getByReleaseCodeIn(List<String> releaseCodes) throws Exception;

    /**
     * 根据发布记录code与学科获取 能力信息
     * @param releaseCode
     * @param subjectCode
     * @return
     * @throws Exception
     */
    List<SubjectAbilityModel> getByReleaseCodeAndSubjectCode(String releaseCode,String subjectCode) throws Exception;

}
