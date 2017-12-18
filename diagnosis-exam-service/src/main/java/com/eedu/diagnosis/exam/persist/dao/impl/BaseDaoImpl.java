package com.eedu.diagnosis.exam.persist.dao.impl;

import com.eedu.diagnosis.common.utils.GenericsUtils;
import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.exam.persist.dao.BaseDao;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class BaseDaoImpl<T> implements BaseDao<T> {
    private Class<T> entityClass;
    Logger logger = null;


    @Autowired
    protected SqlSessionTemplate sessionTemplate;

    @SuppressWarnings("unchecked")
    public BaseDaoImpl() {
        entityClass = GenericsUtils.getSuperClassGenricType(this.getClass());
    }

    public void delete(T entity)  throws Exception{
        String CRUD_DELETEBYID = entityClass.getSimpleName() + "_deleteByPrimaryKey";
        Serializable id = (Serializable) GenericsUtils.getFieldValue(entity, "getCode");
        sessionTemplate.delete(CRUD_DELETEBYID, id);

        this.logMybatisSql(CRUD_DELETEBYID, id);
    }

    public void delete(Serializable id)  throws Exception{
        String CRUD_DELETEBYID = entityClass.getSimpleName() + "_deleteByPrimaryKey";
        sessionTemplate.delete(CRUD_DELETEBYID, id);
		this.logMybatisSql(CRUD_DELETEBYID, id);
    }

    public void delete(Serializable... ids)  throws Exception{
        String CRUD_BATCH_DELEtEBYID = entityClass.getSimpleName() + "_batch_deleteById";
        List<Object> list = new ArrayList<Object>();
        for (Serializable id : ids) {
            list.add(id);
        }
        sessionTemplate.delete(CRUD_BATCH_DELEtEBYID, list);
        this.logMybatisSql(CRUD_BATCH_DELEtEBYID, list);
    }

    @Override
    public void delete(List<?> ids)  throws Exception{
        String CRUD_BATCH_DELEtEBYID = entityClass.getSimpleName() + "_batch_deleteById";
        sessionTemplate.delete(CRUD_BATCH_DELEtEBYID, ids);
        this.logMybatisSql(CRUD_BATCH_DELEtEBYID, ids);
    }

    public void delete()  throws Exception{
        String CRUD_DELETE_ALL = entityClass.getSimpleName() + "_batch_delete_all";
        sessionTemplate.delete(CRUD_DELETE_ALL);
        this.logMybatisSql(CRUD_DELETE_ALL,null);
    }

    public List<T> listByIds(Serializable... ids)  throws Exception{
        String CRUD_BATCH_DELEtEBYID = entityClass.getSimpleName() + "_batch_listByIds";
        List<Object> list = new ArrayList<Object>();
        for (Serializable id : ids) {
            list.add(id);
        }
        this.logMybatisSql(CRUD_BATCH_DELEtEBYID,list);
        return sessionTemplate.selectList(CRUD_BATCH_DELEtEBYID, list);
    }

    public List<T> listByIds(List<Object> list)  throws Exception{
        String CRUD_BATCH_DELEtEBYID = entityClass.getSimpleName() + "_batch_listByIds";
        this.logMybatisSql(CRUD_BATCH_DELEtEBYID,list);
        return sessionTemplate.selectList(CRUD_BATCH_DELEtEBYID, list);
    }

    public List<T> findByCondition(Map<String, Object> queryMap, Order order)  throws Exception{
        String CRUD_FINDBYPAGER = entityClass.getSimpleName() + "_findByParameter";
        if (null != order) {
            queryMap.put("orderProperty", order.getProperty());
            queryMap.put("orderDirection", order.getDirection());
        }
        this.logMybatisSql(CRUD_FINDBYPAGER, queryMap);
        List<T> list = sessionTemplate.selectList(CRUD_FINDBYPAGER, queryMap);
        return list;
    }


    public T get(Serializable id)  throws Exception{
        String CRUD_GETBYID = entityClass.getSimpleName() + "_getById";
        this.logMybatisSql(CRUD_GETBYID, id);

        return sessionTemplate.selectOne(CRUD_GETBYID, id);
    }

    public T get(String value, String type)  throws Exception{
        String CRUD_GETBYID = entityClass.getSimpleName() + "_" + type + "_getByValue";
        this.logMybatisSql(CRUD_GETBYID, type);
        return sessionTemplate.selectOne(CRUD_GETBYID, value);
    }

    public List<T> getAll()  throws Exception{
        String CRUD_GETALL = entityClass.getSimpleName() + "_getAll";

        this.logMybatisSql(CRUD_GETALL, null);

        return sessionTemplate.selectList(CRUD_GETALL);
    }

    public void save(T entity)  throws Exception{
        String CRUD_SAVE = entityClass.getSimpleName() + "_insert";
        sessionTemplate.insert(CRUD_SAVE, entity);

        this.logMybatisSql(CRUD_SAVE, entity);
    }

    public void saveList(List<T> list)  throws Exception{
        String CRUD_BATCH_SAVE = entityClass.getSimpleName() + "_batch_insert";
        sessionTemplate.insert(CRUD_BATCH_SAVE, list);
//        this.logMybatisSql(CRUD_BATCH_SAVE, list);
    }

    public void update(T entity)  throws Exception{
        String CRUD_UPDATE = entityClass.getSimpleName() + "_updateByPrimaryKeySelective";
        sessionTemplate.update(CRUD_UPDATE, entity);
        this.logMybatisSql(CRUD_UPDATE, entity);
    }

    /**
     * 打印sql日志
     */
    protected void logMybatisSql(String id, Object parameterObject) {
        try {
            MappedStatement ms = sessionTemplate.getConfiguration().getMappedStatement(id);
            BoundSql boundSql = ms.getBoundSql(parameterObject);

            logger = Logger.getLogger(GenericsUtils.getSuperClassGenricType(entityClass));
            logger.debug(boundSql.getSql().trim());
            logger.debug(boundSql.getParameterObject());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
