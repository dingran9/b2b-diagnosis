package com.eeduspace.report.service.impl;

import com.eeduspace.report.model.ReleaseViewData;
import com.eeduspace.report.model.ResultsDistributionModel;
import com.eeduspace.report.po.ExamPo;
import com.eeduspace.report.po.StudentSubjectScorePo;
import com.eeduspace.report.po.UserAnswerResultPo;
import com.eeduspace.report.service.UnitResearchReportService;
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
 * Created by liuhongfei on 2017/9/28.
 */
@Service
public class UnitResearchReportServiceImpl implements UnitResearchReportService{

    @Autowired
    private EntityManagerFactory emf;

    /**
     * 获取视图数据
     *
     * @param unitCode 单元code
     * @return
     * @throws Exception
     */
    @Override
    public List<ReleaseViewData> getReleaseViewData(String unitCode,Integer districtId,String semester) throws Exception {
        {
            String sql = "SELECT\n" +
                    "\tcode as code ,\n" +
                    " diagnosis_name as diagnosisName,\n" +
                    "\tstart_time as startTime,\n" +
                    "\tend_time as endTime,\n" +
                    "\tschool_code as schoolCode,\n" +
                    "\tschool_name as schoolName,\n" +
                    "\tstage_code as stageCode,\n" +
                    "\tgrade_code as gradeCode,\n" +
                    "\tsubject_code as subjectCode,\n" +
                    "\tteacher_code as teacherCode,\n" +
                    "\tteacher_name as teacherName,\n" +
                    "  art_type as artType,\n" +
                    "  diagnosis_paper_code as diagnosisPaperCode,\n" +
                    "  diagnosis_type as diagnosisType,\n" +
                    "  is_snapshot as isSnapshot,\n" +
                    "  unit_code as unitCode,\n" +
                    "  unit_name as unitName,\n" +
                    "  group_area_district_id as  groupAreaDistrictId,\n" +
                    "  group_area_district_name as groupAreaDistrictName,\n" +
                    "  create_time as createTime,\n" +
                    "\texam_type as examType,\n" +
                    "\trelease_code as releaseCode,\n" +
                    "\trelease_name as releaseName,\n" +
                    "\tsemester as semester,\n" +
                    "\ttotal_score as totalScore,\n" +
                    "\tecode as ecode,\n" +
                    "  difficult_star as difficultStar,\n" +
                    "  sort as sort\n" +
                    "FROM\n" +
                    "\trelease_view\n" +
                    "WHERE  unit_code = ?1 and group_area_district_id = ?2 and semester = ?3 ";

            EntityManager em = emf.createEntityManager();
            try {
                Query query = em.createNativeQuery(sql);
                query.setParameter(1, unitCode);
                query.setParameter(2, districtId);
                query.setParameter(3, semester);
                SQLQuery nativeQuery = query.unwrap(SQLQuery.class);
                // 设置返回值类型
                nativeQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List<ReleaseViewData> result = Lists.newArrayList();
                List<Map<String, Object>> mapResult = nativeQuery.list();
                mapResult.forEach(map -> {
                    ReleaseViewData model = new ReleaseViewData();
                    try {
                        BeanUtils.populate(model, map);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    result.add(model);
                });
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("sql异常----》" + sql);
            } finally {
                em.close();
            }
        }

    }


    /**
     * 获取学生答题记录
     *
     * @param codes
     * @return
     * @throws Exception
     */
    @Override
    public List<ExamPo> getexam(List<Integer> codes) throws Exception {
        {

//            List<ExamPo>  result =  examDao.findByReleaseExamCodeIn(codes);


            String sql="SELECT e.code AS code," +
                    "e.release_exam_code as releaseExamCode," +
                    "e.school_code as schoolCode," +
                    "e.school_name as schoolName," +
                    "e.class_code as classCode," +
                    "e.class_name as className," +
                    "e.grade_code as gradeCode," +
                    "e.grade_name as gradeName," +
                    "e.subject_name as subjectName," +
                    "e.subject_code as subjectCode," +
                    "e.teacher_name as teacherName," +
                    "e.teacher_code as teacherCode," +
                    "e.user_code as userCode," +
                    "e.user_name as userName," +
                    "e.user_make_paper_code as userMakePaperCode," +
                    "e.paper_name as paperName," +
                    "e.paper_code as paperCode," +
                    "e.create_time as createTime," +
                    "e.update_time as updateTime," +
                    "e.book_type_version_code as bookTypeVersionCode," +
                    "e.book_type_version_name as bookTypeVersionName," +
                    "e.art_type as artType  FROM edu_exam e WHERE\n" +
                    "e.release_exam_code in (:codes)";

            EntityManager em = emf.createEntityManager();
            try {
                Query query = em.createNativeQuery(sql);
                query.setParameter("codes", codes);
                SQLQuery nativeQuery = query.unwrap(SQLQuery.class);
                // 设置返回值类型
                nativeQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List<ExamPo> result = Lists.newArrayList();
                List<Map<String, Object>> mapResult = nativeQuery.list();
                mapResult.forEach(map -> {
                    ExamPo model = new ExamPo();
                    try {
                        BeanUtils.populate(model, map);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    result.add(model);
                });
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("sql异常----》" + sql);
            } finally {
                em.close();
            }
        }

    }



    /**
     * 获取学生答题信息
     *
     * @param codes
     * @return
     * @throws Exception
     */
    @Override
    public List<UserAnswerResultPo> getAnswerResult(List<Integer> codes) throws Exception {
        {

//            List<ExamPo>  result =  examDao.findByReleaseExamCodeIn(codes);


            String sql="SELECT e.code as code," +
                    "e.answer_result as answerResult," +
                    "e.score as score," +
                    "e.is_complex as isComplex," +
                    "e.question_sn as questionSn," +
                    "e.exam_code as examCode," +
                    "e.production_json as productionJson," +
                    "e.knowledge_json as knowledgeJson," +
                    "e.question_code as questionCode," +
                    "e.complex_question_code as complexQuestionCode FROM edu_user_answer_result e WHERE\n" +
                    "e.exam_code in (:codes)";

            EntityManager em = emf.createEntityManager();
            try {
                Query query = em.createNativeQuery(sql);
                query.setParameter("codes", codes);
                SQLQuery nativeQuery = query.unwrap(SQLQuery.class);
                // 设置返回值类型
                nativeQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List<UserAnswerResultPo> result = Lists.newArrayList();
                List<Map<String, Object>> mapResult = nativeQuery.list();
                mapResult.forEach(map -> {
                    UserAnswerResultPo model = new UserAnswerResultPo();
                    try {
                        BeanUtils.populate(model, map);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    result.add(model);
                });
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("sql异常----》" + sql);
            } finally {
                em.close();
            }
        }

    }



    /**
     * 获取学生时间答题总分
     *
     * @param codes
     * @return
     * @throws Exception
     */
    @Override
    public List<StudentSubjectScorePo> getstudentTotalScore(List<Integer> codes) throws Exception {
        {
            String sql="SELECT e.code as code," +
                    "e.score as score," +
                    "e.exam_code as examCode," +
                    "e.create_time as createTime," +
                    "e.update_time as updateTime," +
                    "e.standard_score as standardScore," +
                    "e.paper_score as paperScore FROM edu_student_subject_score e WHERE\n" +
                    "e.exam_code in (:codes) order by score desc";

            EntityManager em = emf.createEntityManager();
            try {
                Query query = em.createNativeQuery(sql);
                query.setParameter("codes", codes);
                SQLQuery nativeQuery = query.unwrap(SQLQuery.class);
                // 设置返回值类型
                nativeQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List<StudentSubjectScorePo> result = Lists.newArrayList();
                List<Map<String, Object>> mapResult = nativeQuery.list();
                mapResult.forEach(map -> {
                    StudentSubjectScorePo model = new StudentSubjectScorePo();
                    try {
                        BeanUtils.populate(model, map);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    result.add(model);
                });
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("sql异常----》" + sql);
            } finally {
                em.close();
            }
        }

    }



    /**
     * 获取学生时间答题总分
     *
     * @param codes
     * @return
     * @throws Exception
     */
    @Override
    public List<ResultsDistributionModel> getstudentTotalScoreByGroup(List<Integer> codes, Integer school_code,Integer grade_code, Integer class_code,Integer subjectCode) throws Exception {
        {
            String sql="SELECT\n" +
                    "\tee.school_code as schoolCode,\n" +
                    "\tee.school_name as schoolName,\n" +
                    "\tee.grade_code as gradeCode,\n" +
                    "\tee.grade_name as gradeName,\n" +
                    "\tee.class_code as classCode,\n" +
                    "\tee.class_name as className,\n" +
                    "\tee.art_type as artType,\n" +
                    "\tee.subject_code as subjectCode,\n" +
                    "\tee.subject_name as subjectName,\n" +
                    "\tee.user_code as userCode,\n" +
                    "  esss.score as score,\n" +
                    "esss.paper_score as paperScore\n" +
                    "FROM\n" +
                    "\tedu_exam ee,\n" +
                    "\tedu_student_subject_score esss\n" +
                    "WHERE\n" +
                    "\tee. CODE = esss.exam_code\n" +
                    "AND ee.release_exam_code IN (:codes)";

            if(null != school_code && school_code > 0) {
                sql = sql + " AND ee.school_code =  " + school_code;
            }

            if(null != grade_code && grade_code > 0) {
                sql = sql + " AND ee.grade_code =  " + grade_code;
            }

            if(null != class_code && class_code > 0) {
                sql = sql + " AND ee.class_code =  " + class_code;
            }

            if(null != subjectCode && subjectCode > 0) {
                sql = sql + " AND ee.subject_code =  " + subjectCode;
            }
            EntityManager em = emf.createEntityManager();
            try {
                Query query = em.createNativeQuery(sql);
                query.setParameter("codes", codes);
                SQLQuery nativeQuery = query.unwrap(SQLQuery.class);
                // 设置返回值类型
                nativeQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List<ResultsDistributionModel> result = Lists.newArrayList();
                List<Map<String, Object>> mapResult = nativeQuery.list();
                mapResult.forEach(map -> {
                    ResultsDistributionModel model = new ResultsDistributionModel();
                    try {
                        BeanUtils.populate(model, map);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    result.add(model);
                });
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("sql异常----》" + sql);
            } finally {
                em.close();
            }
        }

    }

    /**
     * 获取多单元视图数据
     *
     * @param unitCodes 多单元code
     * @return
     * @throws Exception
     */
    @Override
    public List<ReleaseViewData> getReleaseViewDataByCodes(List<String> unitCodes,Integer districtId,String semester) throws Exception {
        {
            String sql = "SELECT\n" +
                    "\tcode as code ,\n" +
                    " diagnosis_name as diagnosisName,\n" +
                    "\tstart_time as startTime,\n" +
                    "\tend_time as endTime,\n" +
                    "\tschool_code as schoolCode,\n" +
                    "\tschool_name as schoolName,\n" +
                    "\tstage_code as stageCode,\n" +
                    "\tgrade_code as gradeCode,\n" +
                    "\tsubject_code as subjectCode,\n" +
                    "\tteacher_code as teacherCode,\n" +
                    "\tteacher_name as teacherName,\n" +
                    "  art_type as artType,\n" +
                    "  diagnosis_paper_code as diagnosisPaperCode,\n" +
                    "  diagnosis_type as diagnosisType,\n" +
                    "  is_snapshot as isSnapshot,\n" +
                    "  unit_code as unitCode,\n" +
                    "  unit_name as unitName,\n" +
                    "  group_area_district_id as  groupAreaDistrictId,\n" +
                    "  group_area_district_name as groupAreaDistrictName,\n" +
                    "  create_time as createTime,\n" +
                    "\texam_type as examType,\n" +
                    "\trelease_code as releaseCode,\n" +
                    "\trelease_name as releaseName,\n" +
                    "\tsemester as semester,\n" +
                    "\ttotal_score as totalScore,\n" +
                    "\tecode as ecode,\n" +
                    "  difficult_star as difficultStar,\n" +
                    "  sort as sort\n" +
                    "FROM\n" +
                    "\trelease_view\n" +
                    "WHERE group_area_district_id = ?1 and semester = ?2 and unit_code in (:unitCodes)";

            EntityManager em = emf.createEntityManager();
            try {
                Query query = em.createNativeQuery(sql);
                query.setParameter(1, districtId);
                query.setParameter(2, semester);
                query.setParameter("unitCodes", unitCodes);
                SQLQuery nativeQuery = query.unwrap(SQLQuery.class);
                // 设置返回值类型
                nativeQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                List<ReleaseViewData> result = Lists.newArrayList();
                List<Map<String, Object>> mapResult = nativeQuery.list();
                mapResult.forEach(map -> {
                    ReleaseViewData model = new ReleaseViewData();
                    try {
                        BeanUtils.populate(model, map);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    result.add(model);
                });
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("sql异常----》" + sql);
            } finally {
                em.close();
            }
        }

    }

}
