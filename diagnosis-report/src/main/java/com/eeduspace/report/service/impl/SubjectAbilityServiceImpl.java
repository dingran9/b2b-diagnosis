package com.eeduspace.report.service.impl;

import com.eeduspace.report.dao.SubjectAbilityDao;
import com.eeduspace.report.model.SubjectAbilityModel;
import com.eeduspace.report.po.SubjectAbilityPo;
import com.eeduspace.report.service.SubjectAbilityService;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
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
 * <p>描述</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/17 9:31
 **/
@Service
public class SubjectAbilityServiceImpl implements SubjectAbilityService{
    @Autowired
    private SubjectAbilityDao subjectAbilityDao;
    @Autowired
    private EntityManagerFactory emf;
    @Override
    public SubjectAbilityPo save(SubjectAbilityPo subjectAbilityPo) {
        return subjectAbilityDao.save(subjectAbilityPo);
    }

    @Override
    public List<SubjectAbilityPo> batchSave(List<SubjectAbilityPo> subjectAbilityPoList) {
        return subjectAbilityDao.save(subjectAbilityPoList);
    }

    /**
     * 获取学习能力
     *
     * @param releaseExamCode 发布考试记录code
     * @param teacherCode     教师code
     * @param subjectCode     学科code
     * @param  semester 学期
     * @param schoolCode 学校code
     * @param gradeCode 学年code
     * @return
     */
    @Override
    public List<SubjectAbilityModel> getByReleaseExamCodeAndTeacherCodeAndSubjectCode(String releaseExamCode, String teacherCode, String subjectCode,List<String> examTypes,String semester,String schoolCode,String gradeCode) throws Exception{
        int position=1;
        Map<Integer,Object> queryMap=new HashMap<Integer,Object>();
        StringBuilder sql=new StringBuilder("SELECT e.user_code as userCode,\n" +
                "sa.ability_code as abilityCode ,\n" +
                "sa.ability_name as abilityName,\n" +
                "sa.ability_score as abilityScore,\n" +
                "e.class_code as classCode,\n" +
                "e.teacher_code as teacherCode,\n"+
                "e.teacher_name as teacherName,\n"+
                "e.release_exam_code as releaseExamCode,\n"+
                "e.class_name as className,\n" +
                "re.create_time as releaseExamCreateTime \n" +
                "FROM \n" +
                "edu_exam e,\n" +
                "edu_release_exam re,\n" +
                "edu_subject_ability sa \n" +
                "WHERE\n" +
                " e.release_exam_code=re.`code`\n" +
                " AND \n" +
                "sa.exam_code=e.`code`\n");
        if(StringUtils.isNotBlank(releaseExamCode)){
            sql.append("AND re.release_code = ?"+position+"\n");
            queryMap.put(position,releaseExamCode);
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
        if(StringUtils.isNotBlank(teacherCode)){
            sql.append("AND e.teacher_code = ?"+position+"\n");
            queryMap.put(position,teacherCode);
            position++;
        }
        if(StringUtils.isNotBlank(semester)){
            sql.append("AND re.semester = ?"+position+"\n");
            queryMap.put(position,semester);
            position++;
        }
        if(!examTypes.isEmpty()){
            for (String s : examTypes) {
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
            SQLQuery nativeQuery = query.unwrap(SQLQuery.class);
            // 设置返回值类型
            nativeQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            return getSubjectAbilityModels(nativeQuery);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("sql异常----》"+sql);
        } finally {
            em.close();
        }
    }

    /**
     * 获取学习能力
     *
     * @param releaseExamCode 发布考试记录code
     * @param subjectCode     学科code
     * @return
     * @throws Exception
     */
    @Override
    public List<SubjectAbilityModel> getByReleaseExamCodeAndSubjectCode(String releaseExamCode, String subjectCode) throws Exception {
        return getByReleaseExamCodeAndTeacherCodeAndSubjectCode(releaseExamCode,null,subjectCode,new ArrayList<>(),null,null,null);
    }

    /**
     * 获取学科能力水平信息
     *
     * @param releaseExamCode 考试发布记录code
     * @param subjectCode     学科code
     * @param examTypes       考试类型（查询不包含的）
     *                        @param semester 学期
     * @return
     * @throws Exception
     */
    @Override
    public List<SubjectAbilityModel> getByReleaseExamCodeAndSubjectCodeAndExamTypeAndSemester(String releaseExamCode, String subjectCode, List<String> examTypes,String semester) throws Exception {
        return getByReleaseExamCodeAndTeacherCodeAndSubjectCode(releaseExamCode,null,subjectCode,examTypes,semester,null,null);
    }


    /**
     * 获取能力信息根据
     *
     * @param subjectCode 学科code
     * @param examTypes   考试类型
     * @param semester    学期
     *                    @param teacherCode 教师code
     * @return
     * @throws Exception
     */
    @Override
    public List<SubjectAbilityModel> getBySubjectCodeAndExamTypeAndSemesterAndTeacherCode(String subjectCode, List<String> examTypes, String semester,String teacherCode) throws Exception {
        return getByReleaseExamCodeAndTeacherCodeAndSubjectCode(null,teacherCode,subjectCode,examTypes,semester,null,null);
    }


    /**
     * 获取能力信息根据
     *
     * @param schoolCode  学校code
     * @param gradeCode   学年code
     * @param subjectCode 学科code
     * @param examTypes   考试类型
     * @param semester    学期
     * @return
     * @throws Exception
     */
    @Override
    public List<SubjectAbilityModel> getBySchoolCodeAndGradeCodeAndSubjectCodeAndExamTypeAndSemester(String schoolCode, String gradeCode, String subjectCode, List<String> examTypes, String semester) throws Exception {
        return getByReleaseExamCodeAndTeacherCodeAndSubjectCode(null,null,subjectCode,examTypes,semester,schoolCode,gradeCode);
    }


    /**
     * 根据发布记录code获取 能力信息
     *
     * @param releaseCodes 发布记录code
     * @return
     * @throws Exception
     */
    @Override
    public List<SubjectAbilityModel> getByReleaseCodeIn(List<String> releaseCodes) throws Exception {
        StringBuilder sql=new StringBuilder("SELECT e.user_code as userCode,\n" +
                            "                sa.ability_code as abilityCode ,\n" +
                            "                sa.ability_name as abilityName,\n" +
                            "								sa.ability_score as abilityScore,\n" +
                            "                e.class_code as classCode,\n" +
                            "								e.school_code as schoolCode,\n" +
                            "								e.school_name as schoolName,\n" +
                            "								e.grade_code as gradeCode,\n" +
                            "                e.teacher_code as teacherCode,\n" +
                            "                e.teacher_name as teacherName,\n" +
                            "                e.subject_code as subjectCode,\n" +
                            "                e.release_exam_code as releaseExamCode,\n" +
                            "                e.class_name as className,\n" +
                            "                re.create_time as releaseExamCreateTime \n" +
                            "                FROM \n" +
                            "                edu_exam e,\n" +
                            "                edu_release_exam re,\n" +
                            "                edu_subject_ability sa \n" +
                            "                WHERE\n" +
                            "                 e.release_exam_code=re.`code`\n" +
                            "                 AND \n" +
                            "                sa.exam_code=e.`code`\n" +
                            "AND re.release_code in (:codes)");
        EntityManager em = emf.createEntityManager();
        try {
            Query query=em.createNativeQuery(sql.toString());
            query.setParameter("codes",releaseCodes);
            SQLQuery nativeQuery = query.unwrap(SQLQuery.class);
            nativeQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            return getSubjectAbilityModels(nativeQuery);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("sql异常----》"+sql);
        } finally {
            em.close();
        }
    }







    /**
     * 处理数据库返回数据映射
     * @param nativeQuery
     * @return
     */
    private List<SubjectAbilityModel> getSubjectAbilityModels(SQLQuery nativeQuery) {
        List<SubjectAbilityModel> subjectAbilityModels = Lists.newArrayList();
        nativeQuery.list().forEach(map->{
            SubjectAbilityModel subjectAbilityModel=new SubjectAbilityModel();
            try {
                BeanUtils.populate(subjectAbilityModel, (Map<String, ? extends Object>) map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            subjectAbilityModels.add(subjectAbilityModel);
        });
        return  subjectAbilityModels;
    }

    /**
     * 根据发布记录code与学科获取 能力信息
     *
     * @param releaseCode
     * @param subjectCode
     * @return
     * @throws Exception
     */
    @Override
    public List<SubjectAbilityModel> getByReleaseCodeAndSubjectCode(String releaseCode, String subjectCode) throws Exception {
        StringBuilder sql=new StringBuilder("SELECT e.user_code as userCode,\n" +
                            "                sa.ability_code as abilityCode ,\n" +
                            "                sa.ability_name as abilityName,\n" +
                            "								sa.ability_score as abilityScore,\n" +
                            "                e.class_code as classCode,\n" +
                            "								e.school_code as schoolCode,\n" +
                            "								e.school_name as schoolName,\n" +
                            "								e.grade_code as gradeCode,\n" +
                            "                e.teacher_code as teacherCode,\n" +
                            "                e.teacher_name as teacherName,\n" +
                            "                e.subject_code as subjectCode,\n" +
                            "                e.release_exam_code as releaseExamCode,\n" +
                            "                e.class_name as className,\n" +
                            "                re.create_time as releaseExamCreateTime \n" +
                            "                FROM \n" +
                            "                edu_exam e,\n" +
                            "                edu_release_exam re,\n" +
                            "                edu_subject_ability sa \n" +
                            "                WHERE\n" +
                            "                 e.release_exam_code=re.`code`\n" +
                            "                 AND \n" +
                            "                sa.exam_code=e.`code`\n" +
                            "AND re.release_code =:code AND e.subject_code =:subjectCode");
        EntityManager em = emf.createEntityManager();
        try {
            Query query=em.createNativeQuery(sql.toString());
            query.setParameter("code",releaseCode);
            query.setParameter("subjectCode",subjectCode);
            SQLQuery nativeQuery = query.unwrap(SQLQuery.class);
            nativeQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            return getSubjectAbilityModels(nativeQuery);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("sql异常----》"+sql);
        } finally {
            em.close();
        }
    }
}
