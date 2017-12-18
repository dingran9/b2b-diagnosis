package com.eedu.diagnosis.paper.persist.dao.impl;

import com.eedu.diagnosis.common.utils.GenericsUtils;
import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.paper.persist.dao.BaseDao;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

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
	public BaseDaoImpl(){
		entityClass = GenericsUtils.getSuperClassGenricType(this.getClass());
	}

	public void delete(T entity) {
		String CRUD_DELETEBYID = entityClass.getSimpleName()+"_deleteById";
		Assert.notNull(entity);
		Serializable id = (Serializable) GenericsUtils.getFieldValue(entity, "getId");
		Assert.notNull(id);
		sessionTemplate.delete(CRUD_DELETEBYID, id);
		
		//this.logMybatisSql(CRUD_DELETEBYID, id);
	}

	public void delete(Serializable id) {
		String CRUD_DELETEBYID = entityClass.getSimpleName()+"_deleteById";
		Assert.notNull(id);
		sessionTemplate.delete(CRUD_DELETEBYID, id);
//		this.logMybatisSql(CRUD_DELETEBYID, id);
	}

	public void delete(Serializable...ids) {
		String CRUD_BATCH_DELEtEBYID = entityClass.getSimpleName()+"_batch_deleteById";
		Assert.notNull(ids);
		List<Object> list = new ArrayList<Object>();
		for(Serializable id: ids){
			list.add(id);
		}
		sessionTemplate.delete(CRUD_BATCH_DELEtEBYID, list);
	}

	public void delete() {
		String CRUD_DELETE_ALL = entityClass.getSimpleName()+"_batch_delete_all";
		sessionTemplate.delete(CRUD_DELETE_ALL);
	}

	public List<T> findByCondition(Map<String, Object> queryMap,Order order) {
		if(order==null){
			order=new Order();
		}
		String CRUD_FINDBYPAGER = entityClass.getSimpleName()+"_findByParameter";
		queryMap.put("orderProperty", order.getProperty());
		queryMap.put("orderDirection", order.getDirection());
		List<T> list = sessionTemplate.selectList(CRUD_FINDBYPAGER, queryMap);
		this.logMybatisSql(CRUD_FINDBYPAGER, queryMap);
		return list;
	}

	 public List<T> listByIds(Serializable... ids) {
	        String CRUD_BATCH_DELEtEBYID = entityClass.getSimpleName()+"_batch_listByIds";
	        Assert.notNull(ids);
	        List<Object> list = new ArrayList<Object>();
	        for (Serializable id : ids) {
	            list.add(id);
	        }
	        return sessionTemplate.selectList(CRUD_BATCH_DELEtEBYID, list);
	    }

	    public List<T> listByIds(List<?> list) {
	        String CRUD_BATCH_DELEtEBYID = entityClass.getSimpleName()+"_batch_listByIds";
	        return sessionTemplate.selectList(CRUD_BATCH_DELEtEBYID, list);
	    }

	public T get(Serializable id) {
		String CRUD_GETBYID = entityClass.getSimpleName()+"_getById";
		Assert.notNull(id);
		
		//this.logMybatisSql(CRUD_GETBYID, id);
		
		return sessionTemplate.selectOne(CRUD_GETBYID, id);
	}
    public T get(String value,String type) {
        String CRUD_GETBYID = entityClass.getSimpleName()+"_"+type+"_getByValue";

        return sessionTemplate.selectOne(CRUD_GETBYID, value);
    }
	public List<T> getAll() {
		String CRUD_GETALL = entityClass.getSimpleName()+"_getAll";
		
		//this.logMybatisSql(CRUD_GETALL, null);
		
		return sessionTemplate.selectList(CRUD_GETALL);
	}

	public int save(T entity) {
		String CRUD_SAVE = entityClass.getSimpleName()+"_save";
		Assert.notNull(entity);
		return sessionTemplate.insert(CRUD_SAVE, entity);
		
		//this.logMybatisSql(CRUD_SAVE, entity);
	}

	public void saveList(List<T> list) {
		String CRUD_BATCH_SAVE = entityClass.getSimpleName()+"_save";
		Assert.notNull(list);
		
		for(T t: list){
			sessionTemplate.insert(CRUD_BATCH_SAVE, t);
		}
	}

	public int update(T entity) {
		String CRUD_UPDATE = entityClass.getSimpleName()+"_update";
		Assert.notNull(entity);
		return sessionTemplate.update(CRUD_UPDATE, entity);
	}
	
	/**打印sql日志*/
	protected void logMybatisSql(String id, Object parameterObject){
		try{
			MappedStatement ms = sessionTemplate.getConfiguration().getMappedStatement(id);
	        BoundSql boundSql = ms.getBoundSql(parameterObject);
	        
	        logger = Logger.getLogger(GenericsUtils.getSuperClassGenricType(entityClass));
	        logger.info(boundSql.getSql().trim());
	        logger.info(boundSql.getParameterObject());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
