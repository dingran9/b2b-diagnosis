package com.eeduspace.report.service.impl;

import com.eeduspace.report.model.ResultsChangModel;
import com.eeduspace.report.service.ReportstatisticsService;
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
 * Created by liuhongfei on 2017/4/25.
 */
@Service
public class ReportstatisticsServiceImpl implements ReportstatisticsService {

    @Autowired
    private EntityManagerFactory emf;

    /**
     * 各班学生总分成绩变化情况 (高一，高二要区分文理科  文理类型  0 无类型  1文  2理)
     * @param releaseExamCode 发布code
     * @return
     * @throws Exception
     */
//    @Override
//    public List<ResultsChangModel> getResultsChang(String artType, String releaseExamCode)throws Exception{
//
//
//        String sql = "SELECT\n" +
//                "\tROUND(\n" +
//                "\t\ta.sum_score / a.num_stu ,\n" +
//                "\t\t2\n" +
//                "\t) AS proportion,\n" +
//                "\ta.class_name,\n" +
//                "\tdate_format(a.create_time,'%Y-%c-%d %h:%i:%s') as create_time,\n" +
//                "\ta.release_code\n" +
//                "FROM\n" +
//                "\t(\n" +
//                "\t\tSELECT\n" +
//                "\t\t\te.create_time,\n" +
//                "\t\t\tsum(er.score) AS sum_score,\n" +
//                "\t\t\tcount(user_code) AS num_stu,\n" +
//                "\t\t\tem.class_name,\n" +
//                "\t\t\te.release_code\n" +
//                "\t\tFROM\n" +
//                "\t\t\tedu_release_exam e,\n" +
//                "\t\t\tedu_exam em,\n" +
//                "\t\t\tedu_report er\n" +
//                "\t\tWHERE\n" +
//                "\t\t\te.`code` = em.release_exam_code\n" +
//                "\t\tAND em.`code` = er.exam_code\n" +
//                "\t\tAND e.release_code IN (\n" +
//                "\t\t\tSELECT\n" +
//                "\t\t\t\tb.release_code\n" +
//                "\t\t\tFROM\n" +
//                "\t\t\t\tedu_release_exam a,\n" +
//                "\t\t\t\tedu_release_exam b\n" +
//                "\t\t\tWHERE\n" +
//                "\t\t\t\ta.release_code = ?1 \n" +
//                "\t\t\tAND a.semester = b.semester and b.exam_type <> '0' \n" +
//                "\t\t) \n" ;
//
//               if(null != artType && !"".equals(artType)) sql = sql +" and em.art_type = '"+artType+"'\n" ;
//
//            sql = sql+    "\t\tGROUP BY\n" +
//                "\t\t\te.release_code,\n" +
//                "\t\t\tem.class_code\n" +
//                "\t) a";
//        System.out.println("sql --- "+sql);
//        EntityManager em = emf.createEntityManager();
//        try {
//            Query query = em.createNativeQuery(sql);
//            query.setParameter(1, releaseExamCode);
//            SQLQuery nativeQuery = query.unwrap(SQLQuery.class);
//            // 设置返回值类型
//            nativeQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//            List<ResultsChangModel> resultsChangModels = Lists.newArrayList();
//            List<Map<String, Object>> mapResult = nativeQuery.list();
//            mapResult.forEach(map -> {
//                ResultsChangModel model = new ResultsChangModel();
//                try {
//                    BeanUtils.populate(model, map);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//                resultsChangModels.add(model);
//            });
//            return resultsChangModels;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new Exception("sql异常----》" + sql);
//        } finally {
//            em.close();
//        }
//
//    }


    /**
     * 各班学生总分成绩变化情况 (高一，高二要区分文理科  文理类型  0 无类型  1文  2理)
     * @param artType 文理科类型  文科 1 , 理科  2
     * @param releaseExamCode 发布code
     * @return
     * @throws Exception
     */
    @Override
    public List<ResultsChangModel> getResultsChang(String artType, String releaseExamCode)throws Exception{


        String sql = "\tSELECT\n" +
                "\t\t\tdate_format(e.create_time,'%Y-%c-%d %H:%i:%s') as create_time,\n" +
                "\t\t\tsum( er.score ) AS score,\n" +
                "\t\t\tem.class_name,\n" +
                "      em.user_code,\n" +
                "      e.code,\n" +
                "      e.release_code\n" +
                "\t\tFROM\n" +
                "\t\t\tedu_release_exam e,\n" +
                "\t\t\tedu_exam em,\n" +
                "\t\t\tedu_student_subject_score er\n" +
                "\t\tWHERE\n" +
                "\t\t\te.`code` = em.release_exam_code\n" +
                "\t\tAND em.`code` = er.exam_code\n" +
                "\t\tAND e.release_code IN (\n" +
                "\t\t\tSELECT\n" +
                "\t\t\t\tb.release_code\n" +
                "\t\t\tFROM\n" +
                "\t\t\t\tedu_release_exam a,\n" +
                "\t\t\t\tedu_release_exam b\n" +
                "\t\t\tWHERE\n" +
                "\t\t\t\ta.release_code = ?1 \n" +
                "\t\t\tAND a.semester = b.semester and b.exam_type <> '0' \n" +
                "\t\t) " ;

        if(null != artType && !"".equals(artType) && !"0".equals(artType)) sql = sql +" and em.art_type = '"+artType+"'\n" ;

        sql = sql+ " GROUP BY e.release_code, em.user_code ORDER BY e.create_time,em.class_name";

//        System.out.println(sql);

        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, releaseExamCode);
            SQLQuery nativeQuery = query.unwrap(SQLQuery.class);
            // 设置返回值类型
            nativeQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List<ResultsChangModel> resultsChangModels = Lists.newArrayList();
            List<Map<String, Object>> mapResult = nativeQuery.list();
            mapResult.forEach(map -> {
                ResultsChangModel model = new ResultsChangModel();
                try {
                    BeanUtils.populate(model, map);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                resultsChangModels.add(model);
            });
            return resultsChangModels;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("sql异常----》" + sql);
        } finally {
            em.close();
        }

    }



    /**
     * 获取发布考试下的学科班级教学成绩
     *  @param type 单次或学期标记  all为查询学期
     * @param releaseExamCode 发布code
     * @return
     * @throws Exception
     */
    @Override
    public List<ResultsChangModel> getTeachingachievement(String type,String releaseExamCode)throws Exception{


        String sql = "\tSELECT\n" +
                "\t\t\tdate_format(e.create_time,'%Y-%c-%d %H:%i:%s') as create_time,\n" +
                "\t\t\ter.score AS score,\n" +
                "\t\t\tem.class_name,\n" +
                "      em.user_code,\n" +
                "      em.subject_code,\n" +
                "      e.code,\n" +
                "      er.paper_score,\n" +
                "      e.total_score,\n" +
                "      e.release_code\n" +
                "\t\tFROM\n" +
                "\t\t\tedu_release_exam e,\n" +
                "\t\t\tedu_exam em,\n" +
                "\t\t\tedu_student_subject_score er\n" +
                "\t\tWHERE\n" +
                "\t\t\te.`code` = em.release_exam_code\n" +
                "\t\tAND em.`code` = er.exam_code\n" ;

         if("all".equals(type)){
            sql = sql + "\t\tAND e.release_code IN (\n" +
                    "\t\t\tSELECT\n" +
                    "\t\t\t\tb.release_code\n" +
                    "\t\t\tFROM\n" +
                    "\t\t\t\tedu_release_exam a,\n" +
                    "\t\t\t\tedu_release_exam b\n" +
                    "\t\t\tWHERE\n" +
                    "\t\t\t\ta.release_code = ?1 \n" +
                    "\t\t\tAND a.semester = b.semester and b.exam_type <> '0' \n" +
                    "		)";
         }else sql = sql + " AND e.release_code = ?1 ";

        sql = sql+ "ORDER BY e.code,em.class_name";

        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, releaseExamCode);
            SQLQuery nativeQuery = query.unwrap(SQLQuery.class);
            // 设置返回值类型
            nativeQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List<ResultsChangModel> resultsChangModels = Lists.newArrayList();
            List<Map<String, Object>> mapResult = nativeQuery.list();
            mapResult.forEach(map -> {
                ResultsChangModel model = new ResultsChangModel();
                try {
                    BeanUtils.populate(model, map);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                resultsChangModels.add(model);
            });
            return resultsChangModels;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("sql异常----》" + sql);
        } finally {
            em.close();
        }

    }


    /**
     * 获取单次考试发布班级总平均分
     * @param releaseExamCode
     * @return
     * @throws Exception
     */
    @Override
    public List<ResultsChangModel> getAverageClassaverage(String releaseExamCode)throws Exception{
        {


            String sql = " SELECT\n" +
                    "\tROUND(\n" +
                    "\t\ta.sum_score / a.num_stu ,\n" +
                    "\t\t2\n" +
                    "\t) AS proportion,\n" +
                    "\ta.class_name,\n" +
                    "\tdate_format(a.create_time,'%Y-%c-%d %H:%i:%s') as create_time,\n" +
                    "\ta.release_code\n" +
                    "FROM\n" +
                    "\t(\n" +
                    "\t\tSELECT\n" +
                    "\t\t\te.create_time,\n" +
                    "\t\t\tsum(er.score) AS sum_score,\n" +
                    "\t\t\tcount(user_code) AS num_stu,\n" +
                    "\t\t\tem.class_name,\n" +
                    "\t\t\te.release_code\n" +
                    "\t\tFROM\n" +
                    "\t\t\tedu_release_exam e,\n" +
                    "\t\t\tedu_exam em,\n" +
                    "\t\t\tedu_report er\n" +
                    "\t\tWHERE\n" +
                    "\t\t\te.`code` = em.release_exam_code\n" +
                    "\t\tAND em.`code` = er.exam_code\n" +
                    "\t\tAND e.release_code = ?1 \n" +
                    "\t\tGROUP BY\n" +
                    "\t\t\te.release_code,\n" +
                    "\t\t\tem.class_code\n" +
                    "\t) a";

            EntityManager em = emf.createEntityManager();
            try {
                Query query = em.createNativeQuery(sql);
                query.setParameter(1, releaseExamCode);
                SQLQuery nativeQuery = query.unwrap(SQLQuery.class);
                // 设置返回值类型
                nativeQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List<ResultsChangModel> resultsChangModels = Lists.newArrayList();
                List<Map<String, Object>> mapResult = nativeQuery.list();
                mapResult.forEach(map -> {
                    ResultsChangModel model = new ResultsChangModel();
                    try {
                        BeanUtils.populate(model, map);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    resultsChangModels.add(model);
                });
                return resultsChangModels;
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("sql异常----》" + sql);
            } finally {
                em.close();
            }

        }


    }


    /**
     * 获取单次考试发布班级科目平均分
     * @param releaseExamCode
     * @return
     * @throws Exception
     */
    @Override
    public List<ResultsChangModel> getClassSubjectaverage(String releaseExamCode)throws Exception{

        {

            String sql = " SELECT\n" +
                    "\tROUND(\n" +
                    "\t\ta.sum_score / a.num_stu ,\n" +
                    "\t\t2\n" +
                    "\t) AS proportion,\n" +
                    "\ta.class_name,\n" +
                    "  a.subject_code,\n" +
                    "\tdate_format(a.create_time,'%Y-%c-%d %H:%i:%s') as create_time,\n" +
                    "\ta.release_code\n" +
                    "FROM\n" +
                    "\t(\n" +
                    "\t\tSELECT\n" +
                    "\t\t\te.create_time,\n" +
                    "\t\t\tsum(er.score) AS sum_score,\n" +
                    "\t\t\tcount(user_code) AS num_stu,\n" +
                    "\t\t\tem.class_name,\n" +
                    "\t\t\te.release_code,\n" +
                    "      em.subject_code\n" +
                    "\t\tFROM\n" +
                    "\t\t\tedu_release_exam e,\n" +
                    "\t\t\tedu_exam em,\n" +
                    "\t\t\tedu_report er\n" +
                    "\t\tWHERE\n" +
                    "\t\t\te.`code` = em.release_exam_code\n" +
                    "\t\tAND em.`code` = er.exam_code\n" +
                    "\t\tAND e.release_code = ?1 \n" +
                    "\t\tGROUP BY\n" +
                    "\t\t\te.release_code,\n" +
                    "\t\t\tem.class_code,\n" +
                    "      em.subject_code\n" +
                    "\t) a" ;

//        System.out.println("sql --- "+sql);
            EntityManager em = emf.createEntityManager();
            try {
                Query query = em.createNativeQuery(sql);
                query.setParameter(1, releaseExamCode);
                SQLQuery nativeQuery = query.unwrap(SQLQuery.class);
                // 设置返回值类型
                nativeQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List<ResultsChangModel> resultsChangModels = Lists.newArrayList();
                List<Map<String, Object>> mapResult = nativeQuery.list();
                mapResult.forEach(map -> {
                    ResultsChangModel model = new ResultsChangModel();
                    try {
                        BeanUtils.populate(model, map);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    resultsChangModels.add(model);
                });
                return resultsChangModels;
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("sql异常----》" + sql);
            } finally {
                em.close();
            }

        }

    }






}
