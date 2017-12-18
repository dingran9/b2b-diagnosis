package com.eeduspace.report.service.impl;

import com.eeduspace.b2b.report.model.report.KnowledgeModuleModel;
import com.eeduspace.report.dao.KnowledgeModuleDao;
import com.eeduspace.report.po.KnowledgeModulePo;
import com.eeduspace.report.service.KnowledgeModuleService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 知识点模块
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-03-17 11:49
 **/
@Service
public class KnowledgeModuleServiceImpl implements KnowledgeModuleService{
    @Autowired
    private KnowledgeModuleDao knowledgeModuleDao;
    @Autowired
    private EntityManagerFactory emf;
    /**
     * 保存
     *
     * @param knowledgeModulePo
     * @return
     */
    @Override
    public KnowledgeModulePo save(KnowledgeModulePo knowledgeModulePo) {
        return knowledgeModuleDao.save(knowledgeModulePo);
    }

    @Override
    public List<KnowledgeModulePo> batchSave(List<KnowledgeModulePo> knowledgeModulePoList) {
        return knowledgeModuleDao.save(knowledgeModulePoList);
    }

    /**
     * 获取知识点模块统计信息
     *
     * @param releaseExamCode 发布考试记录code
     * @param teacherCode     教师code
     * @param subjectCode     学科code
     * @return
     */
    @Override
    public List<KnowledgeModuleModel> findByReleaseExamCodeAndSubjectCodeAndTeacherCode(String releaseExamCode, String teacherCode, String subjectCode) throws Exception{
        int position=1;
        Map<Integer,Object> queryMap=new HashMap<Integer,Object>();
        StringBuilder sql=new StringBuilder("SELECT km.knowledge_module_score as knowledgeModuleScore,km.exam_code as examCode," +
                "km.knowledge_module_name as knowledgeModuleName," +
                            "e.teacher_code as teacherCode,e.teacher_name as teacherName,"+
                            "km.knowledge_module_code as knowledgeModuleCode" +
                " FROM edu_exam e,edu_release_exam re,edu_knowledge_module km WHERE e.release_exam_code=re.`code` AND km.exam_code=e.`code` \n");




        if(StringUtils.isNotBlank(teacherCode)){
            sql.append("AND e.teacher_code = ?"+position+"\n");
            queryMap.put(position,teacherCode);
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


        EntityManager em = emf.createEntityManager();
        try {
            Query query=em.createNativeQuery(sql.toString());
            for (Map.Entry<Integer, Object> integer : queryMap.entrySet()) {
                query.setParameter(integer.getKey(), integer.getValue());
            }
            SQLQuery nativeQuery = query.unwrap(SQLQuery.class);
            // 设置返回值类型
            nativeQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List<KnowledgeModuleModel> knowledgeModels = Lists.newArrayList();
            List<Map<String,Object>> mapResult=nativeQuery.list();
            mapResult.forEach(map -> {
                KnowledgeModuleModel moduleModel=new KnowledgeModuleModel();
                try {
                    BeanUtils.populate(moduleModel,map);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                knowledgeModels.add(moduleModel);
            });
            return  knowledgeModels;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("sql异常----》"+sql);
        } finally {
            em.close();
        }
    }

    /**
     * 获取模块掌握情况
     *
     * @param codes 发布记录主键集合
     * @return
     * @throws Exception
     */
    @Override
    public List<KnowledgeModuleModel> findByReleaseCodeIn(List<String> codes) throws Exception {
        StringBuilder sql=new StringBuilder("SELECT e.school_code as schoolCode,e.school_name as schoolName, km.knowledge_module_score as knowledgeModuleScore,km.exam_code as examCode," +
                            "km.knowledge_module_name as knowledgeModuleName," +
                            "e.teacher_code as teacherCode,e.teacher_name as teacherName,"+
                            "e.grade_code as gradeCode,"+
                            "e.class_code as classCode,e.class_name as className,"+
                            "km.knowledge_module_code as knowledgeModuleCode" +
                            " FROM edu_exam e,edu_release_exam re,edu_knowledge_module km WHERE e.release_exam_code=re.`code` AND km.exam_code=e.`code` \n");

        sql.append("AND re.release_code in (:codes)");
        EntityManager em = emf.createEntityManager();
        try {
            Query query=em.createNativeQuery(sql.toString());
            query.setParameter("codes",codes);
            SQLQuery nativeQuery = query.unwrap(SQLQuery.class);
            // 设置返回值类型
            nativeQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List<KnowledgeModuleModel> knowledgeModels = Lists.newArrayList();
            List<Map<String,Object>> mapResult=nativeQuery.list();
            mapResult.forEach(map -> {
                KnowledgeModuleModel moduleModel=new KnowledgeModuleModel();
                try {
                    BeanUtils.populate(moduleModel,map);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                knowledgeModels.add(moduleModel);
            });
            return  knowledgeModels;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("sql异常----》"+sql);
        } finally {
            em.close();
        }
    }

    /**
     * 获取模块掌握情况
     *
     * @param releaseCode 发布记录code
     * @param subjectCode 学科code
     * @return
     * @throws Exception
     */
    @Override
    public List<KnowledgeModuleModel> findByReleaseCodeAndSubjectCode(String releaseCode, String subjectCode) throws Exception {
        StringBuilder sql=new StringBuilder("SELECT e.school_code as schoolCode,e.school_name as schoolName, km.knowledge_module_score as knowledgeModuleScore,km.exam_code as examCode," +
                            "km.knowledge_module_name as knowledgeModuleName," +
                            "e.teacher_code as teacherCode,e.teacher_name as teacherName,"+
                            "e.grade_code as gradeCode,"+
                            "e.subject_code as subjectCode,"+
                            "e.class_code as classCode,e.class_name as className,"+
                            "km.knowledge_module_code as knowledgeModuleCode" +
                            " FROM edu_exam e,edu_release_exam re,edu_knowledge_module km WHERE e.release_exam_code=re.`code` AND km.exam_code=e.`code` \n");

        sql.append("AND re.release_code  =:code");
        sql.append(" AND e.subject_code =:subjectCode");
        EntityManager em = emf.createEntityManager();
        try {
            Query query=em.createNativeQuery(sql.toString());
            query.setParameter("code",releaseCode);
            query.setParameter("subjectCode",subjectCode);
            SQLQuery nativeQuery = query.unwrap(SQLQuery.class);
            // 设置返回值类型
            nativeQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List<KnowledgeModuleModel> knowledgeModels = Lists.newArrayList();
            List<Map<String,Object>> mapResult=nativeQuery.list();
            mapResult.forEach(map -> {
                KnowledgeModuleModel moduleModel=new KnowledgeModuleModel();
                try {
                    BeanUtils.populate(moduleModel,map);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                knowledgeModels.add(moduleModel);
            });
            return  knowledgeModels;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("sql异常----》"+sql);
        } finally {
            em.close();
        }
    }

    /**
     * 获取知识点模块信息
     *
     * @param releaseExamCode 发布考试记录code
     * @param subjectCode     学科code
     * @return
     * @throws Exception
     */
    @Override
    public List<KnowledgeModuleModel> findByReleaseExamCodeAndSubjectCode(String releaseExamCode, String subjectCode) throws Exception {
        return findByReleaseExamCodeAndSubjectCodeAndTeacherCode(releaseExamCode,null,subjectCode);
    }
}
