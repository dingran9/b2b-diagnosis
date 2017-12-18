package com.eeduspace.report.service.impl;

import com.eeduspace.report.dao.ExamDao;
import com.eeduspace.report.model.TeacherModel;
import com.eeduspace.report.po.ExamPo;
import com.eeduspace.report.service.ExamService;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

/**
 * <p>描述</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/17 9:31
 */
@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    private ExamDao examDao;
    @Autowired
    private EntityManagerFactory emf;
    @Override
    public ExamPo save(ExamPo examPo) {
        return examDao.save(examPo);
    }

    @Override
    public List<TeacherModel> getTeacherByReleaseExamCode(String releaseExamCode) throws Exception{
        String sql="SELECT e.subject_code as subjectCode,e.teacher_code as teacherCode,e.teacher_name as teacherName FROM edu_exam e,edu_release_exam re WHERE\n" +
                "e.release_exam_code=re.`code`\n" +
                "AND re.release_code=?1\n" +
                "GROUP BY subject_code,teacher_name";

        EntityManager em = emf.createEntityManager();
        try {
            Query query=em.createNativeQuery(sql);
            query.setParameter(1, releaseExamCode);
            SQLQuery nativeQuery = query.unwrap(SQLQuery.class);
            // 设置返回值类型
            nativeQuery.setResultTransformer(Transformers.aliasToBean(TeacherModel.class));
            List<TeacherModel> teacherModels = nativeQuery.list();
            return  teacherModels;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("sql异常----》"+sql);
        } finally {
            em.close();
        }
    }
}

