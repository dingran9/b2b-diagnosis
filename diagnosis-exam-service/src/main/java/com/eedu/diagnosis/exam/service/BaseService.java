package com.eedu.diagnosis.exam.service;

import com.eedu.diagnosis.common.utils.Order;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/** 
* <p>类功能说明: 基本服务封装</p>
* @version V1.0
*/

public abstract interface BaseService<T> {


	/**
	 * <p>存储实体</p>
	 * @param entity  实体
	 */
	public abstract void save(T entity) throws Exception;

	
	/**
	 * <p>批量存储实体</p>
	 * @param list  实体列表
	 */
	public abstract void saveList(List<T> list) throws Exception;

	
	/**
	 * <p>更新实体</p>
	 * @param entity  实体
	 */
	public abstract void update(T entity) throws Exception;

	
	/**
	 * <p>批量更新实体</p>
	 * @param list  实体列表
	 */
	public abstract void updateList(List<T> list) throws Exception;

	
	/**
	 * <p>根据ID查找实体</p>
	 * @param id  实体编号
	 * @return  如果查到返回实体，否则返回null
	 */
	public abstract T get(Serializable id) throws Exception;

	
	/**
	 * <p>查询所有的</p>
	 * @return
	 */
	public abstract List<T> getAll() throws Exception;

	
	/**
	 * <p>删除实体</p>
	 * @param entity 实体
	 */
	public abstract void delete(T entity) throws Exception;
	
	
	/**
	 * <p>根据id删除实体</p>
	 * @param id
	 */
	public abstract void delete(Serializable id) throws Exception;

	
	/**
	 * <p>批量删除</p>
	 * @param ids  编号数组
	 */
	public abstract void delete(Serializable... ids) throws Exception;
	/**
	 * <p>批量删除</p>
	 * @param ids  编号数组
	 */
	public abstract void delete(List<?> ids) throws Exception;


	/**
	 * <p>删除所有的数据</p>
	 */
	public abstract void delete() throws Exception;
	
	

	/**
	 * <p>条件查询</p>
	 * @param queryMap  查询条件信息
	 */
	public abstract List<T> findByCondition(Map<String, Object> queryMap, Order order) throws Exception;
	
	/**
	 * <p>条件查询</p>
	 * @param queryMap  查询条件信息
	 */
	public abstract List<T> findByCondition(Map<String, Object> queryMap) throws Exception;
	/**
	 * <p>通过ids查询</p>
	 * @return List<T>
	 */
	List<T> listByIds(List<Object> list) throws Exception;

	List<T> listByIds(Serializable...ids) throws Exception;
}
