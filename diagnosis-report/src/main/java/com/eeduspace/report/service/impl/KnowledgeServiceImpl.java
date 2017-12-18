package com.eeduspace.report.service.impl;

import com.eeduspace.report.dao.KnowledgeDao;
import com.eeduspace.report.model.KnowledgeModel;
import com.eeduspace.report.po.KnowledgePo;
import com.eeduspace.report.service.KnowledgeService;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * <p>描述</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/17 9:31
 **/
@Service
public class KnowledgeServiceImpl implements KnowledgeService{
    @Autowired
    private KnowledgeDao knowledgeDao;
    @Autowired
    private EntityManagerFactory emf;
    @Override
    public KnowledgePo save(KnowledgePo knowledgePo) {
        return knowledgeDao.save(knowledgePo);
    }

    @Override
    public List<KnowledgePo> batchSave(List<KnowledgePo> knowledgePoList) {
        return knowledgeDao.save(knowledgePoList);
    }

    @Override
    public List<KnowledgeModel> findByReleaseExamCodeAndTeacherCodeAndSubjectCode(String releaseExamCode, String teacherCode, String subjectCode) throws Exception {
        String sql="SELECT e.user_code as userCode, e.class_code as classCode,e.class_name as className,k.knowledge_name as knowledgeName,k.knowledge_code as knowledgeCode,k.is_right as isRight FROM edu_exam e,edu_release_exam re,edu_knowledge k WHERE e.release_exam_code=re.`code` AND k.exam_code=e.`code` AND e.subject_code=?1 AND re.release_code=?2 AND e.teacher_code=?3";
        EntityManager em = emf.createEntityManager();
        try {
            Query query=em.createNativeQuery(sql);
            query.setParameter(1, subjectCode);
            query.setParameter(2, releaseExamCode);
            query.setParameter(3, teacherCode);

            SQLQuery nativeQuery = query.unwrap(SQLQuery.class);
            // 设置返回值类型
            nativeQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List<KnowledgeModel> knowledgePoList = Lists.newArrayList();
            nativeQuery.list().forEach(map->{
                KnowledgeModel knowledgeModel=new KnowledgeModel();
                try {
                    BeanUtils.populate(knowledgeModel, (Map<String, ? extends Object>) map);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                knowledgePoList.add(knowledgeModel);
            });
            return  knowledgePoList;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("sql异常----》"+sql);
        } finally {
            em.close();
        }
    }
}
