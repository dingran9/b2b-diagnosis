package com.eeduspace.report.service.impl;

import com.eeduspace.report.dao.ThreeColorDao;
import com.eeduspace.report.model.ThreeColorModel;
import com.eeduspace.report.po.ThreeColorPo;
import com.eeduspace.report.service.ThreeColorService;
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
 * <p>描述</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/17 9:31
 **/
@Service
public class ThreeColotServiceImpl implements ThreeColorService{
    @Autowired
    private ThreeColorDao threeColorDao;
    @Autowired
    private EntityManagerFactory emf;
    @Override
    public ThreeColorPo save(ThreeColorPo threeColorPo) {
        return threeColorDao.save(threeColorPo);
    }

    @Override
    public List<ThreeColorPo> batchSave(List<ThreeColorPo> threeColorPoList) {
        return threeColorDao.save(threeColorPoList);
    }

    /**
     * 获取三色图组合信息
     *
     * @param releaseExamCode 发布考试记录cde
     * @param subjectCode     学科code
     * @return
     */
    @Override
    public List<ThreeColorModel> getByCondition(String releaseExamCode, String subjectCode) throws Exception{
        int position=1;
        Map<Integer,Object> queryMap=new HashMap<Integer,Object>();
        StringBuilder sql=new StringBuilder("SELECT \n" +
                "tc.bule_score as buleScore,\n" +
                "tc.gray_score as grayScore,\n" +
                "tc.orange_score as orangeScore,\n" +
                "tc.module_code as moduleCode,\n"+
                "tc.module_name as moduleName,\n" +
                "e.class_code as classCode,\n" +
                "e.class_name as className,\n" +
                "e.grade_code as gradeCode,\n" +
                "e.release_exam_code as releaseExamCode,\n" +
                "e.subject_code as subjectCode\n" +
                "FROM edu_exam e,edu_release_exam re,edu_three_color tc\n" +
                "WHERE 1=1\n" +
                "AND\n" +
                "e.release_exam_code=re.`code`\n" +
                "AND\n" +
                "e.`code`=tc.exam_code\n");
        if(StringUtils.isNotBlank(releaseExamCode)){
            sql.append("AND re.release_code = ?"+position+"\n");
            queryMap.put(position,releaseExamCode);
            position++;
        }
        if(StringUtils.isNotBlank(subjectCode)){
            sql.append("AND e.subject_code = ?"+position+"\n");
            queryMap.put(position,subjectCode);
            position++;
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
            List<ThreeColorModel> threeColorModels= Lists.newArrayList();

            resultMap.forEach(map -> {
                ThreeColorModel threeColorModel=new ThreeColorModel();
                try {
                    BeanUtils.populate(threeColorModel,map);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                threeColorModels.add(threeColorModel);
            });
            return  threeColorModels;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("sql异常----》"+sql);
        } finally {
            em.close();
        }
    }

    /**
     * 获取三色图组合信息
     *
     * @param releaseExamCode 发布考试记录cde
     * @param subjectCode     学科code
     * @return
     */
    @Override
    public List<ThreeColorModel> findByReleaseExamCodeAndSubjectCode(String releaseExamCode, String subjectCode) throws Exception {
        return getByCondition(releaseExamCode,subjectCode);
    }
}
