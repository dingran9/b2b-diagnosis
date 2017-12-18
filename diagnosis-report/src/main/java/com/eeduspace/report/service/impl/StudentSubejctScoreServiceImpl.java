package com.eeduspace.report.service.impl;

import com.eeduspace.b2b.report.model.StudentGetScoreDo;
import com.eeduspace.b2b.report.model.report.StudentMakeResultModel;
import com.eeduspace.report.dao.StudentSubjectScoreDao;
import com.eeduspace.report.po.StudentSubjectScorePo;
import com.eeduspace.report.service.StudentSubjectScoreService;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学生做卷结果
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-03-18 11:19
 **/
@Service
public class StudentSubejctScoreServiceImpl implements StudentSubjectScoreService{
    @Autowired
    private StudentSubjectScoreDao studentSubjectScoreDao;
    @Autowired
    private EntityManagerFactory emf;
    @Override
    public StudentSubjectScorePo save(StudentSubjectScorePo studentSubjectScorePo) {
        return studentSubjectScoreDao.save(studentSubjectScorePo);
    }

    /**
     * 获取 学生答卷得分信息
     *
     * @param releaseCodes 发布记录code 外键
     * @param subjectCode 学科code
     * @return
     * @throws Exception
     */
    @Override
    public List<StudentGetScoreDo> findByReleaseCodesIn(List<String> releaseCodes,String subjectCode) throws Exception {
        StrBuilder sql = new StrBuilder("SELECT re.release_code as releaseFKCode,e.class_code as classCode,e.class_name as className,e.teacher_code as teacherCode,e.teacher_name as teacherName,e.school_code as schoolCode,e.school_name as schoolName,e.release_exam_code as releaseCode,esss.score \n" +
                            "FROM edu_exam e,edu_student_subject_score esss,edu_release_exam re\n" +
                            "WHERE e.`code` = esss.exam_code\n" +
                            "AND re.`code`=e.release_exam_code\n" +
                            "AND re.release_code IN (:codes)  AND e.subject_code =:subjectCode");
        EntityManager em = emf.createEntityManager();
        try {
            Query query=em.createNativeQuery(sql.toString());
            query.setParameter("codes",releaseCodes);
            query.setParameter("subjectCode",subjectCode);
            // 设置返回值类型
            SQLQuery sqlQuery = query.unwrap(SQLQuery.class);//.setResultTransformer(Transformers.aliasToBean(StudentMakeResultModel.class));
            sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List<Map<String, Object>> resultMap=sqlQuery.list();
            List<StudentGetScoreDo> studentGetScoreDos= Lists.newArrayList();
            resultMap.forEach(map -> {
                StudentGetScoreDo studentGetScoreDo=new StudentGetScoreDo();
                try {
                    BeanUtils.populate(studentGetScoreDo,map);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                studentGetScoreDos.add(studentGetScoreDo);
            });
            return studentGetScoreDos;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("sql异常----》"+sql);
        } finally {
            em.close();
        }
    }

    @Override
    public List<StudentGetScoreDo> findByReleaseExamCodesIn(List<Integer> releaseExamCodes) throws Exception {
        StrBuilder sql = new StrBuilder("SELECT e.class_code as classCode,e.class_name as className,e.teacher_code as teacherCode,e.teacher_name as teacherName,e.school_code as schoolCode,e.school_name as schoolName,e.release_exam_code as releaseExamCode,esss.score FROM edu_exam e,edu_student_subject_score esss\n" +
                            "WHERE\n" +
                            "e.`code` = esss.exam_code\n" +
                            "AND \n" +
                            "e.release_exam_code IN (:codes)");
        EntityManager em = emf.createEntityManager();
        try {
            Query query=em.createNativeQuery(sql.toString());
            query.setParameter("codes",releaseExamCodes);
            // 设置返回值类型
            SQLQuery sqlQuery = query.unwrap(SQLQuery.class);//.setResultTransformer(Transformers.aliasToBean(StudentMakeResultModel.class));
            sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List<Map<String, Object>> resultMap=sqlQuery.list();
            List<StudentGetScoreDo> studentGetScoreDos= Lists.newArrayList();
            resultMap.forEach(map -> {
                StudentGetScoreDo studentGetScoreDo=new StudentGetScoreDo();
                try {
                    BeanUtils.populate(studentGetScoreDo,map);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                studentGetScoreDos.add(studentGetScoreDo);
            });
            return studentGetScoreDos;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("sql异常----》"+sql);
        } finally {
            em.close();
        }
    }

    /**
     * 获取 学生答卷得分信息
     *
     * @param releaseExamCode 发布记录code
     * @param subjectCode     学科code
     * @return
     * @throws Exception
     */
    @Override
    public List<StudentGetScoreDo> findByReleaseExamCodeAndSubjectCode4Stage(String releaseExamCode, String subjectCode) throws Exception {
        StrBuilder sql = new StrBuilder("SELECT e.subject_code as subjectCode,e.class_code as classCode,e.class_name as className,e.teacher_code as teacherCode,e.teacher_name as teacherName,e.school_code as schoolCode,e.school_name as schoolName,e.release_exam_code as releaseCode,esss.score FROM edu_exam e,edu_student_subject_score esss\n" +
                            "WHERE\n" +
                            "e.`code` = esss.exam_code\n" +
                            "AND \n" +
                            "e.release_exam_code =:code AND e.subject_code =:subjectCode");
        EntityManager em = emf.createEntityManager();
        try {
            Query query=em.createNativeQuery(sql.toString());
            query.setParameter("code",releaseExamCode);
            query.setParameter("subjectCode",subjectCode);
            // 设置返回值类型
            SQLQuery sqlQuery = query.unwrap(SQLQuery.class);//.setResultTransformer(Transformers.aliasToBean(StudentMakeResultModel.class));
            sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List<Map<String, Object>> resultMap=sqlQuery.list();
            List<StudentGetScoreDo> studentGetScoreDos= Lists.newArrayList();
            resultMap.forEach(map -> {
                StudentGetScoreDo studentGetScoreDo=new StudentGetScoreDo();
                try {
                    BeanUtils.populate(studentGetScoreDo,map);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                studentGetScoreDos.add(studentGetScoreDo);
            });
            return studentGetScoreDos;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("sql异常----》"+sql);
        } finally {
            em.close();
        }
    }

    /**
     * 获取 学生答卷结果信息
     *
     * @param releaseExamCode 发布考试记录code
     * @param teacherCode     教师code 为null时 统计所有班级
     * @param  subjectCode 学科code
     * @return
     */
    @Override

    public List<StudentMakeResultModel> findByReleaseExamCode(String releaseExamCode, String teacherCode, String subjectCode) throws Exception{
        return findByCondition(releaseExamCode,teacherCode,subjectCode,null,null,new ArrayList<>(),null,null);
    }

    /**
     * 获取 学生答卷结果信息
     *
     * @param releaseExamCode 发布考试记录code
     * @param teacherCode     教师code
     * @param subjectCode     学科code
     * @param classCode       班级code
     *                        @param semester 学期
     *                        @param examType 考试类型
     *                        @param schoolCode 学校code
     *                        @param gradeCode 学年code
     * @return
     * @throws Exception
     */
    @Override
    public List<StudentMakeResultModel> findByCondition(String releaseExamCode, String teacherCode, String subjectCode, String classCode,String semester,List<String> examType,String schoolCode,String gradeCode) throws Exception {
        int position=1;
        Map<Integer,Object> queryMap=new HashMap<Integer,Object>();
        StringBuilder sql=new StringBuilder("SELECT sss.score ,sss.standard_score as standardScore,sss.paper_score as paperScore," +
                " e.book_type_version_code as bookTypeVersionCode,e.book_type_version_name as bookTypeVersionName," +
                "e.class_code as classCode,e.art_type as artType,e.class_name as className,e.create_time as createTime," +
                "e.grade_code as gradeCode,e.grade_name as gradeName,e.paper_code as paperCode," +
                "e.paper_name as paperName,e.release_exam_code as releaseExamCode,e.school_code as schooleCode,e.school_name as schooleName," +
                "e.subject_code as subjectCode,e.subject_name as subjectName,e.teacher_code as teacherCode,e.teacher_name as teacherName," +
                "e.user_code as userCode,e.user_make_paper_code as userMakePaperCode,e.user_name as userName," +
                "re.release_code as releaseCode," +
                "re.create_time as releaseCrateTime\n"+
                "FROM edu_exam e,edu_release_exam re,edu_student_subject_score sss \n" +
                "WHERE e.release_exam_code=re.`code` \n" +
                "AND e.`code`=sss.exam_code \n");
        if(StringUtils.isNotBlank(teacherCode)){
            sql.append("AND e.teacher_code = ?"+position+"\n");
            queryMap.put(position,teacherCode);
            position++;
        }
        if(StringUtils.isNotBlank(schoolCode)){
            sql.append("AND e.school_code = ?"+position+"\n");
            queryMap.put(position,schoolCode);
            position++;
        }
        if(StringUtils.isNotBlank(gradeCode)){
            sql.append("AND e.grade_code = ?"+position+"\n");
            queryMap.put(position,gradeCode);
            position++;
        }

        if(StringUtils.isNotBlank(subjectCode)){
            sql.append("AND e.subject_code = ?"+position+"\n");
            queryMap.put(position,subjectCode);
            position++;
        }
        if(StringUtils.isNotBlank(releaseExamCode)){
            sql.append("AND re.release_code = ?"+position+"\n");
            queryMap.put(position,releaseExamCode);
            position++;
        }
        if(StringUtils.isNotBlank(classCode)){
            sql.append("AND e.class_code = ?"+position+"\n");
            queryMap.put(position,classCode);
            position++;
        }
        if(StringUtils.isNotBlank(semester)){
            sql.append("AND re.semester = ?"+position+"\n");
            queryMap.put(position,semester);
            position++;
        }
        if(examType.size()>0){
            for (String s : examType) {
                sql.append("AND re.exam_type <> ?"+position+"\n");
                queryMap.put(position,s);
                position++;
            }
        }

        EntityManager em = emf.createEntityManager();
        try {
            Query query=em.createNativeQuery(sql.toString());
            for (Map.Entry<Integer, Object> integer : queryMap.entrySet()) {
                query.setParameter(integer.getKey(), integer.getValue());
            }
            SQLQuery sqlQuery = query.unwrap(SQLQuery.class);//.setResultTransformer(Transformers.aliasToBean(StudentMakeResultModel.class));
            // 设置返回值类型
            sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List<Map<String, Object>> resultMap=sqlQuery.list();
            List<StudentMakeResultModel> studentMakeResultModels= Lists.newArrayList();

            resultMap.forEach(map -> {
                StudentMakeResultModel studentMakeResultModel=new StudentMakeResultModel();
                try {
                    BeanUtils.populate(studentMakeResultModel,map);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                studentMakeResultModels.add(studentMakeResultModel);
            });
            return  studentMakeResultModels;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("sql异常----》"+sql);
        } finally {
            em.close();
        }
    }

    /**
     * 通过发布记录code 与学科code 获取答题信息
     *
     * @param releaseExamCode 通过发布记录code
     * @param subjectCode     学科code
     * @return
     * @throws Exception
     */
    @Override
    public List<StudentMakeResultModel> findByReleaseExamCodeAndSubjectCode(String releaseExamCode, String subjectCode) throws Exception {
        return findByCondition(releaseExamCode,null,subjectCode,null,null,new ArrayList<>(),null,null);
    }

    /**
     * 获取答题信息
     *
     * @param releaseExamCode 发布记录code
     * @param semester        学期
     * @param examType        考试类型（不包含的  即不包含的类型）
     * @return
     */
    @Override
    public List<StudentMakeResultModel> findByReleaseExamCodeAndSemesterAndExamType(String releaseExamCode,String subjectCode, String semester, List<String> examType) throws Exception{
        return findByCondition(releaseExamCode,null,subjectCode,null,semester,examType,null,null) ;
    }

    /**
     * 获取答题信息
     *
     * @param releaseExamCode 考试发布记录code
     * @param teacherCode     教师code
     * @param subjectCode     学科code
     * @param classCode       班级code
     * @return
     * @throws Exception
     */
    @Override
    public List<StudentMakeResultModel> findByReleaseExamCodeAndTeacherCodeAndSubjectCodeAndClassCode(String releaseExamCode, String teacherCode, String subjectCode, String classCode) throws Exception {
        return findByCondition(releaseExamCode,teacherCode,subjectCode,classCode,null,new ArrayList<>(),null,null);
    }

    /**
     * 获取答题信息
     *
     * @param schoolCode 学校code
     * @param gradeCode  学年code
     * @param semester   学期code
     * @param examType   考试类型
     *                   @param subjectCode 学科code
     * @return
     * @throws Exception
     */
    @Override
    public List<StudentMakeResultModel> findBySchoolCodeAndGradeCodeAndSubjectCodeAndSemesterAndExamType(String schoolCode, String gradeCode,String subjectCode, String semester, List<String> examType) throws Exception {
        return findByCondition(null,null,subjectCode,null,semester,examType,schoolCode,gradeCode);
    }


    /**
     * 获取答题信息
     *
     * @param releaseExamCode 发布记录code
     * @param subjectCode     学科code
     * @param classCode       班级code
     * @return
     * @throws Exception
     */
    @Override
    public List<StudentMakeResultModel> findByReleaseExamCodeAndSubjectCodeAndClassCode(String releaseExamCode, String subjectCode, String classCode) throws Exception {
        return findByReleaseExamCodeAndTeacherCodeAndSubjectCodeAndClassCode(releaseExamCode,null,subjectCode,classCode);
    }
}
