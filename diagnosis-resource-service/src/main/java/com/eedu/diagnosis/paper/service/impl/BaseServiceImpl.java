package com.eedu.diagnosis.paper.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.paper.persist.dao.BaseDao;
import com.eedu.diagnosis.paper.service.BaseService;

/** 
 * <p>类功能说明: 系统服务封装类</p>
 * <p>Title: BaseServiceImpl.java</p> 
 * @version V1.0
 */


public class BaseServiceImpl<T> implements BaseService<T> {
	
	protected BaseDao<T> baseDao;
	
	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}


	/**
	 * <p>存储实体</p>
	 * @param entity  实体
	 */
	@Transactional
	public int save(T entity){
		return this.baseDao.save(entity);
	}

	
	/**
	 * <p>批量存储实体</p>
	 * @param list  实体列表
	 */
	@Transactional
	public void saveList(List<T> list){
		this.baseDao.saveList(list);
	}

	
	/**
	 * <p>更新实体</p>
	 * @param entity  实体
	 */
	@Transactional
	public int update(T entity){
		return this.baseDao.update(entity);
	}

	
	/**
	 * <p>批量更新实体</p>
	 * @param list  实体列表
	 */
	@Transactional
	public void updateList(List<T> list){
		for(T t: list) {
			this.baseDao.update(t);
		}
	}

	
	/**
	 * <p>根据ID查找实体</p>
	 * @param id  实体编号
	 * @return  如果查到返回实体，否则返回null
	 */
	@Transactional(readOnly=true)
	public T get(Serializable id){
		return this.baseDao.get(id);
	}

	 @Override
	    public List<T> listByIds(List<?> list) {
	        return this.baseDao.listByIds(list);
	    }

	    @Override
	    public List<T> listByIds(Serializable... ids) {
	        return this.baseDao.listByIds(ids);
	    }
	
	/**
	 * <p>查询所有的</p>
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<T> getAll(){
		return this.baseDao.getAll();
	}

	
	/**
	 * <p>删除实体</p>
	 * @param entity 实体
	 */
	@Transactional
	public void delete(T entity){
		this.baseDao.delete(entity);
	}
	
	
	/**
	 * <p>根据id删除实体</p>
	 * @param id
	 */
	@Transactional
	public void delete(Serializable id){
		this.baseDao.delete(id);
	}

	
	/**
	 * <p>批量删除</p>
	 * @param ids  编号数组
	 */
	@Transactional
	public void delete(Serializable...ids){
		this.baseDao.delete(ids);
	}
	
	/**
	 * <p>删除所有的数据</p>
	 */
	@Transactional
	public void delete(){
		this.baseDao.delete();
	}
	
	


	/**
	 * <p>条件查询</p>
	 * @param queryMap  查询条件信息
	 */
	@Transactional(readOnly=true)
	public List<T> findByCondition(Map<String, Object> queryMap,Order order){
		if(order==null){
			order=new Order();
		}
		return this.baseDao.findByCondition(queryMap,order);
	}


	@Override
	public List<T> findByCondition(Map<String, Object> queryMap) {
		return this.findByCondition(queryMap, null);
	}
}
