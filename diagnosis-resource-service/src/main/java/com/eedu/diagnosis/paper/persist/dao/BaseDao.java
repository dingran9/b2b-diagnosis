package com.eedu.diagnosis.paper.persist.dao;

import com.eedu.diagnosis.common.utils.Order;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface BaseDao<T> {

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
	 * <p>根据ID查找实体</p>
	 * @param id  实体编号
	 * @return  如果查到返回实体，否则返回null
	 */
	public abstract T get(Serializable id);

    /**
     * 更加外键查找实体
     * @param value
     * @param type
     * @return
     */
    public T get(String value, String type);
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
	 * <p>删除所有的实体</p>
	 */
	public abstract void delete();
	

	/**
	 * <p>条件查询</p>
	 * @param queryMap  查询条件信息
	 * @param order 排序信息
	 */
	public abstract List<T> findByCondition(Map<String, Object> queryMap, Order order);


	/**
	 * <p>查询所有的</p>
	 * @return List<T>
	 */
	public abstract List<T> getAll();
	
	
	/**
     *  <p>通过List查询</p>
     * @return List<T>
     */
    List<T> listByIds(List<?> list);
    
    
	/**
     *  <p>通过ids查询</p>
     * @return List<T>
     */
    List<T> listByIds(Serializable... ids);

	
}
