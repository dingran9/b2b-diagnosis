package com.eeduspace.report.service.impl;

import com.eeduspace.report.dao.ReportDao;
import com.eeduspace.report.model.GradeTotalModel;
import com.eeduspace.report.po.ReportPo;
import com.eeduspace.report.service.ReportService;
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
 *
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/17 9:31
 **/
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportDao reportDao;
    @Autowired
    private EntityManagerFactory emf;

    @Override
    public ReportPo save(ReportPo reportPo) {
        return reportDao.save(reportPo);
    }

    @Override
    public ReportPo findByMakePaperRecordCode(String makePaperRecordCode) {
        return reportDao.findByMakePaperRecordCode(makePaperRecordCode);
    }

    /**
     * 按学生分组查询全学科总分
     *
     * @param releaseExamCode 考试发布code
     * @return
     * @throws Exception
     */
    @Override
    public List<GradeTotalModel> getTotalScoreByGrade(String releaseExamCode) throws Exception {
        {
            String sql = "SELECT\n" +
                    "\tre.release_code,\n" +
                    "\tre.release_name,\n" +
                    "\te.grade_code,\n" +
                    "\te.grade_name,\n" +
                    "\te.user_code,\n" +
                    "\te.user_name,\n" +
                    " e.class_code,\n" +
                    "e.class_name,\n" +
                    "\tsum(r.score) AS totalscore, \n" +
                    "re.total_score AS allscore \n" +
                    "FROM\n" +
                    "\tedu_release_exam re,\n" +
                    "\tedu_exam e,\n" +
                    "\tedu_report r\n" +
                    "WHERE\n" +
                    "\tre.`code` = e.release_exam_code\n" +
                    "AND e.`code` = r.exam_code\n" +
                    "AND re.`release_code` = ?1\n" +
                    "GROUP BY\n" +
                    "\te.user_code ORDER BY e.class_code asc ";

            EntityManager em = emf.createEntityManager();
            try {
                Query query = em.createNativeQuery(sql);
                query.setParameter(1, releaseExamCode);
                SQLQuery nativeQuery = query.unwrap(SQLQuery.class);
                // 设置返回值类型
                nativeQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List<GradeTotalModel> totalScores = Lists.newArrayList();
                List<Map<String, Object>> mapResult = nativeQuery.list();
                mapResult.forEach(map -> {
                    GradeTotalModel model = new GradeTotalModel();
                    try {
                        BeanUtils.populate(model, map);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    totalScores.add(model);
                });
                return totalScores;
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("sql异常----》" + sql);
            } finally {
                em.close();
            }
        }

    }

    /**
     * 按班级分组查询所有学生的平均分
     *
     * @param releaseExamCode 考试发布code
     * @return
     * @throws Exception
     */
    @Override
    public List<GradeTotalModel> getAvgScoreByClass(String releaseExamCode) throws Exception {

        String sql = "SELECT\n" +
                "\tre.release_code,\n" +
                "\tre.release_name,\n" +
                "  e.class_code,\n" +
                "  e.class_name,\n" +
                "  e.subject_code,\n" +
                "  e.subject_name,\n" +
                "  SUM(r.score) as sumscore,\n" +
                "  COUNT(e.user_code) usercount,\n" +
                "\tAVG(r.score) AS avgscore\n" +
                "FROM\n" +
                "\tedu_release_exam re,\n" +
                "\tedu_exam e,\n" +
                "\tedu_report r\n" +
                "WHERE\n" +
                "\tre.`code` = e.release_exam_code\n" +
                "AND e.`code` = r.exam_code\n" +
                "AND re.`release_code` = ?1\n" +
                "GROUP BY e.class_code,e.subject_code ORDER BY e.class_code asc ";
//        System.out.println(sql);
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, releaseExamCode);
            SQLQuery nativeQuery = query.unwrap(SQLQuery.class);
            // 设置返回值类型
            nativeQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List<GradeTotalModel> knowledgeModels = Lists.newArrayList();
            List<Map<String, Object>> mapResult = nativeQuery.list();
            mapResult.forEach(map -> {
                GradeTotalModel model = new GradeTotalModel();
                try {
                    BeanUtils.populate(model, map);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                knowledgeModels.add(model);
            });
            return knowledgeModels;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("sql异常----》" + sql);
        } finally {
            em.close();
        }
    }


    /**
     * 全年级，各学科优秀，及格人数占比
     * @param releaseExamCode 考试发布code
     * @return
     * @throws Exception
     */
    @Override
    public List<GradeTotalModel> getPassingByGrade(String releaseExamCode) throws Exception {

        String sql = "SELECT count(\n" +
                "\t\tCASE\n" +
                "\t\tWHEN b.high_account >= 85 THEN\n" +
                "\t\t\tb.high_account\n" +
                "\t\tEND\n" +
                "\t) AS excellent_count,\n" +
                "\tcount(\n" +
                "\t\tCASE\n" +
                "\t\tWHEN b.high_account >= 60 THEN\n" +
                "\t\t\tb.high_account\n" +
                "\t\tEND\n" +
                "\t) AS passing_count,\n" +
                "\tb.subject_code\n" +
                "FROM\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\tROUND(\n" +
                "\t\t\t\ta.stu_score / a.total_score * 100,\n" +
                "\t\t\t\t2\n" +
                "\t\t\t) AS high_account,\n" +
                "\t\t\ta.subject_code\n" +
                "\t\tFROM\n" +
                "\t\t\t(\n" +
                "\t\t\t\tSELECT\n" +
                "\t\t\t\t\terp.paper_score AS total_score,\n" +
                "\t\t\t\t\tSUM(erp.score) AS stu_score,\n" +
                "\t\t\t\t\te.user_code,\n" +
                "\t\t\t\t\te.user_name,\n" +
                "\t\t\t\t\te.subject_code\n" +
                "\t\t\t\tFROM\n" +
                "\t\t\t\t\tedu_release_exam er,\n" +
                "\t\t\t\t\tedu_exam e,\n" +
                "\t\t\t\t\tedu_student_subject_score erp\n" +
                "\t\t\t\tWHERE\n" +
                "\t\t\t\t\ter.`code` = e.release_exam_code\n" +
                "\t\t\t\tAND e.`code` = erp.exam_code\n" +
                "\t\t\t\tAND er.release_code = ?1\n" +
                "\t\t\t\tGROUP BY\n" +
                "\t\t\t\t\te.user_code,\n" +
                "\t\t\t\t\te.subject_code\n" +
                "\t\t\t) a\n" +
//                "\t\tGROUP BY\n" +
//                "\t\t\ta.subject_code\n" +
                "\t) b\n" +
                "GROUP BY\n" +
                "\tb.subject_code ";

//        System.out.print(sql);

        EntityManager em = emf.createEntityManager();

        try {
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, releaseExamCode);
            SQLQuery nativeQuery = query.unwrap(SQLQuery.class);
            // 设置返回值类型
            nativeQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List<GradeTotalModel> passinglist = Lists.newArrayList();
            List<Map<String, Object>> mapResult = nativeQuery.list();
            mapResult.forEach(map -> {
                GradeTotalModel model = new GradeTotalModel();
                try {
                    BeanUtils.populate(model, map);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                passinglist.add(model);
            });
            return passinglist;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("sql异常----》" + sql);
        } finally {
            em.close();
        }


    }

    /**
     * 获取参加考试的学生数
     *
     * @param releaseExamCode 考试发布code
     * @return
     * @throws Exception
     */
    @Override
    public Integer getTotalPeople(String releaseExamCode) throws Exception {

        Integer count = 0;

        String sql = "select count( *)as count from(\n" +
                        "SELECT\n" +
                        "ee.user_code\n" +
                        "FROM\n" +
                        "\tedu_release_exam e,\n" +
                        "\tedu_exam ee\n" +
                        "WHERE\n" +
                        "\te.`code` = ee.release_exam_code\n" +
                        "AND e.release_code = ?1 \n" +
                        "GROUP BY ee.user_code\n" +
                        ") a";
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, releaseExamCode);
            Object ob = query.getSingleResult();
            count = Integer.parseInt(ob.toString());
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("sql异常----》" + sql);
        }finally{
            em.close();
        }

    }

}
