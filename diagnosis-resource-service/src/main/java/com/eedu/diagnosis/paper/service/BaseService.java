package com.eedu.diagnosis.paper.service;

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
	public abstract int save(T entity);

	
	/**
	 * <p>批量存储实体</p>
	 * @param list  实体列表
	 */
	public abstract void saveList(List<T> list);

	
	/**
	 * <p>更新实体</p>
	 * @param entity  实体
	 */
	public abstract int update(T entity);

	
	/**
	 * <p>批量更新实体</p>
	 * @param list  实体列表
	 */
	public abstract void updateList(List<T> list);

	/**
	 * <p>通过ids查询</p>
	 * @return List<T>
	 */
	public List<T> listByIds(List<?> list);
	
	
	
	public List<T> listByIds(Serializable...ids);
	/**
	 * <p>根据ID查找实体</p>
	 * @param id  实体编号
	 * @return  如果查到返回实体，否则返回null
	 */
	public abstract T get(Serializable id);

	
	/**
	 * <p>查询所有的</p>
	 * @return
	 */
	public abstract List<T> getAll();

	
	/**
	 * <p>删除实体</p>
	 * @param entity 实体
	 */
	public abstract void delete(T entity);
	
	
	/**
	 * <p>根据id删除实体</p>
	 * @param id
	 */
	public abstract void delete(Serializable id);

	
	/**
	 * <p>批量删除</p>
	 * @param ids  编号数组
	 */
	public abstract void delete(Serializable... ids);


	/**
	 * <p>删除所有的数据</p>
	 */
	public abstract void delete();
	
	

	/**
	 * <p>条件查询</p>
	 * @param queryMap  查询条件信息
	 */
	public abstract List<T> findByCondition(Map<String, Object> queryMap, Order order);
	
	/**
	 * <p>条件查询</p>
	 * @param queryMap  查询条件信息
	 */
	public abstract List<T> findByCondition(Map<String, Object> queryMap);
}
