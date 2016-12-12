package com.course.selection.common.base;

import java.util.List;
import java.util.Map;



public interface Dao<E> {
    static final String SUFFIX_INSERT = ".save";
    static final String SUFFIX_DELETE_BY_ID = ".deleteById";
    static final String SUFFIX_DELETE_BY_ENTITY = ".deleteByEntity";
    static final String SUFFIX_UPDATE = ".update";
    static final String SUFFIX_UPDATE_BY_MAP = ".updateByMap";
    static final String SUFFIX_FIND_BY_ID = ".findById";
    static final String SUFFIX_FIND_BY_KEYS = ".findByKeys";
    static final String SUFFIX_FIND_ALL = ".findAll";
    static final String SUFFIX_COUNT = ".count";
    static final String SUFFIX_FIND_LIST = ".findList";
    static final String SUFFIX_SELECT_BY_KEYS = ".selectBykeys";

    /**
     * 保存实体,返回实体id
     * 
     * @param entity
     * @return 实体id
     */
    public int save(E entity);

    /**
     * 使用主建删除实体
     * 
     * @param id
     */
    public void deleteById(Integer id);    
    
    /**
     * 删除实体
     * 
     * @param entity
     */
    
    public void deleteByEntity(E entity);
    
    /**
     * 根据相应的sql标记删除相应的实体
     * 
     * @param statementName xml中配置的sql id
     * @param parameterObject sql中使用的删除条件
     */  
    
    public void delete(String statementName, Object parameterObject);

    /**
     * 更新实体
     * 
     * @param entity
     */
    public void update(E entity);

    /**
     * 根据相应的sql标记更新相应的实体
     * 
     * @param statementName xml中配置的sql id
     * @param parameterObject sql中使用的更新条件
     */
    public void update(String statementName, Object parameterObject);

    /**
     * 根据Map中的参数更新实体
     * 
     * @param map 相应的参数放入map中 
     */
    public void updateByMap(Map<String, Object> map);
    
    /**
     * 根据id查找实体
     * 
     * @param id 相应的实体id
     * @return 相应的实体
     */
    public E findById(Object id);

    public List<E> findAll(Object args);

    public List<E> queryForList(String statement);

    public Integer count(String statementName, Object parameterObject);
    
    public Integer count(Map<String, Object> args);

    public List<E> findList(Map<String, Object> args);

    public Long getSequenceId(String sequence);

    public Map<String,Object> getPageInfo(Map<String,Object> args);
}
