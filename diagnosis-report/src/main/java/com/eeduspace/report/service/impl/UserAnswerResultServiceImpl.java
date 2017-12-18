package com.eeduspace.report.service.impl;

import com.eeduspace.b2b.report.model.report.WrongQuestionRankModel;
import com.eeduspace.report.dao.UserAnswerResultDao;
import com.eeduspace.report.model.StudentAnswerResultModel;
import com.eeduspace.report.po.UserAnswerResultPo;
import com.eeduspace.report.service.UserAnswerResultService;
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
import java.util.stream.Collectors;

/**
 * 用户答题结果信息
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-03-17 17:13
 **/
@Service
public class UserAnswerResultServiceImpl implements UserAnswerResultService{
    @Autowired
    private UserAnswerResultDao userAnswerResultDao;
    @Autowired
    private EntityManagerFactory emf;
    @Override
    public UserAnswerResultPo save(UserAnswerResultPo userAnswerResultPo) {
        return userAnswerResultDao.save(userAnswerResultPo);
    }

    @Override
    public List<UserAnswerResultPo> batchSave(List<UserAnswerResultPo> userAnswerResultPoList) {
        return userAnswerResultDao.save(userAnswerResultPoList);
    }

    /**
     * 获取用户答题结果信息
     *
     * @param releaseExamCode 发布考试记录code
     * @return
     */
    @Override
    public List<StudentAnswerResultModel> findByReleaseExamCode(String releaseExamCode) throws Exception{
        String sql="SELECT uar.answer_result as answerResult,uar.complex_question_code as complexQuestionCode," +
                "uar.is_complex as isComplex,uar.question_code as questionCode," +
                "uar.question_sn as questionSn," +
                "e.book_type_version_code as book_type_version_code,e.book_type_version_name,e.class_code,e.class_name,e.create_time," +
                "e.grade_code,e.grade_name,e.paper_code,e.paper_name,e.release_exam_code,e.school_code,e.school_name," +
                "e.subject_code,e.subject_name,e.teacher_code,e.teacher_name,e.user_code,e.user_make_paper_code,e.user_name ,re.release_code FROM edu_exam e,edu_release_exam re,edu_user_answer_result uar\n" +
                "                WHERE e.release_exam_code=re.`code`\n" +
                "                AND e.`code`=uar.exam_code\n" +
                "                AND re.release_code=?1";
        EntityManager em = emf.createEntityManager();
        try {
            Query query=em.createNativeQuery(sql);
            query.setParameter(1, releaseExamCode);
            SQLQuery nativeQuery = query.unwrap(SQLQuery.class);
            // 设置返回值类型
            nativeQuery.setResultTransformer(Transformers.aliasToBean(StudentAnswerResultModel.class));
            List<StudentAnswerResultModel> studentMakeResultModels = nativeQuery.list();
            return  studentMakeResultModels;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("sql异常----》"+sql);
        } finally {
            em.close();
        }
    }

    @Override
    public List<WrongQuestionRankModel> getWrongQuestionRank(String releaseExamCode, String teacherCode, String subjectCode) throws Exception{
        String sql="SELECT COUNT(*) as wrongCount,uar.question_sn as questionSn,e.class_name as className FROM edu_exam e,edu_release_exam re,edu_user_answer_result uar\n" +
                "                WHERE e.release_exam_code=re.`code`\n" +
                "                AND e.`code`=uar.exam_code\n" +
                "                AND re.release_code=?1\n" +
                "                AND e.teacher_code=?2\n" +
                "                AND e.subject_code=?3\n" +
                "AND uar.answer_result=0 GROUP BY e.class_name,uar.question_sn ORDER BY COUNT(*),uar.question_sn DESC";
        EntityManager em = emf.createEntityManager();
        try {
            Query query=em.createNativeQuery(sql);
            query.setParameter(1, releaseExamCode);
            query.setParameter(2, teacherCode);
            query.setParameter(3, subjectCode);

            SQLQuery nativeQuery = query.unwrap(SQLQuery.class);
            // 设置返回值类型
            nativeQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List<Map<String,Object>> resultMap=nativeQuery.list();
            List<WrongQuestionRankModel> wrongQuestionRankModels = Lists.newArrayList();
            resultMap.forEach(stringObjectMap ->{
                WrongQuestionRankModel wrongQuestionRankModel=new WrongQuestionRankModel();
                try {
                    BeanUtils.populate(wrongQuestionRankModel,stringObjectMap);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                wrongQuestionRankModels.add(wrongQuestionRankModel);
            } );
            return  wrongQuestionRankModels.stream().sorted((o1, o2) -> o2.getWrongCount().compareTo(o1.getWrongCount())).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("sql异常----》"+sql);
        } finally {
            em.close();
        }
    }
}
