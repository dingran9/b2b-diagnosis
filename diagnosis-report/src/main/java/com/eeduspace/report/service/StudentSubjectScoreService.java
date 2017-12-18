package com.eeduspace.report.service;

import com.eeduspace.b2b.report.model.StudentGetScoreDo;
import com.eeduspace.b2b.report.model.report.StudentMakeResultModel;
import com.eeduspace.report.po.StudentSubjectScorePo;

import java.util.List;

/**
 * <p>描述 学生答卷结果</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/18 11:18
 * @param    
 * @return   
**/
public interface StudentSubjectScoreService {
    StudentSubjectScorePo save(StudentSubjectScorePo studentSubjectScorePo);

    /**
     * 获取 学生答卷得分信息
     * @param releaseExamCodes 发布记录codes 主键
     * @return
     * @throws Exception
     */
    List<StudentGetScoreDo> findByReleaseExamCodesIn(List<Integer> releaseExamCodes) throws Exception;


    /**
     *获取 学生答卷得分信息
     * @param releaseCodes 发布记录code 外键
     * @param subjectCode 学科code
     * @return
     * @throws Exception
     */
    List<StudentGetScoreDo> findByReleaseCodesIn(List<String> releaseCodes,String subjectCode) throws Exception;


    /**
     *  获取 学生答卷得分信息
     * @param releaseExamCode 发布记录code
     * @param subjectCode 学科code
     * @return
     * @throws Exception
     */
    List<StudentGetScoreDo> findByReleaseExamCodeAndSubjectCode4Stage(String releaseExamCode,String subjectCode) throws Exception;

    /**
     * 获取 学生答卷结果信息
     * @param releaseExamCode 发布考试记录code
     * @param teacherCode    教师code
     * @param subjectCode 学科code
     * @return
     */
    List<StudentMakeResultModel> findByReleaseExamCode(String releaseExamCode, String teacherCode, String subjectCode) throws  Exception;

    /**
     * 获取 学生答卷结果信息
     * @param releaseExamCode 发布考试记录code
     * @param teacherCode 教师code
     * @param subjectCode 学科code
     * @param classCode 班级code
     * @param semester  学期
     * @param examType  考试类型
     * @param  schoolCode 学校code
     * @param  gradeCode 学年code
     * @return
     * @throws Exception
     */
    List<StudentMakeResultModel> findByCondition(String releaseExamCode, String teacherCode, String subjectCode,String classCode,String semester,List<String> examType,String schoolCode,String gradeCode) throws  Exception;


    /**
     * 通过发布记录code 与学科code 获取答题信息
     * @param releaseExamCode 通过发布记录code
     * @param subjectCode 学科code
     * @return
     * @throws Exception
     */
    List<StudentMakeResultModel> findByReleaseExamCodeAndSubjectCode(String releaseExamCode, String subjectCode) throws  Exception;

    /**
     *获取答题信息
     * @param releaseExamCode 发布记录code
     * @param semester 学期
     *                 @param subjectCode  学科code
     * @param examType  考试类型（不包含的  即不包含的类型 查询的时候）
     * @return
     */
    List<StudentMakeResultModel> findByReleaseExamCodeAndSemesterAndExamType(String releaseExamCode,String subjectCode,String semester,List<String> examType) throws Exception;

    /**
     * 获取答题信息
     * @param releaseExamCode 考试发布记录code
     * @param teacherCode 教师code
     * @param subjectCode 学科code
     * @param classCode  班级code
     * @return
     * @throws Exception
     */
    List<StudentMakeResultModel> findByReleaseExamCodeAndTeacherCodeAndSubjectCodeAndClassCode(String releaseExamCode,String teacherCode,String subjectCode,String classCode) throws Exception;

    /**
     * 获取答题信息
     * @param schoolCode 学校code
     * @param gradeCode 学年code
     * @param semester 学期code
     * @param examType 考试类型
     *                 @param  subjectCode 学科code
     * @return
     * @throws Exception
     */
    List<StudentMakeResultModel> findBySchoolCodeAndGradeCodeAndSubjectCodeAndSemesterAndExamType(String schoolCode,String gradeCode,String subjectCode,String semester,List<String> examType) throws Exception;

    /**
     * 获取答题信息
     * @param releaseExamCode 发布记录code
     * @param subjectCode 学科code
     * @param classCode  班级code
     * @return
     * @throws Exception
     */
    List<StudentMakeResultModel> findByReleaseExamCodeAndSubjectCodeAndClassCode(String releaseExamCode,String subjectCode,String classCode) throws Exception;

}
