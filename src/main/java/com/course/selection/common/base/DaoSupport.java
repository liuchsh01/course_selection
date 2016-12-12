package com.course.selection.common.base;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * DAO抽象
 * 
 * @param <E>
 */
public abstract class DaoSupport<E> implements Dao<E> {

    private Class<E> entityClass;

    @Autowired
    private SqlSession sqlSession;

    @SuppressWarnings( "unchecked" )
    private Class<E> getEntityClass() {
        if (entityClass == null) {
            entityClass =
                    (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
                            .getActualTypeArguments()[0];
        }
        return entityClass;
    }

    /**
     * @return the sqlSession
     */
    public SqlSession getSqlSession() {
        return sqlSession;
    }

    /**
     * @param sqlSession the sqlSession to set
     */
    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    /**
     * @param suffix
     * @return
     */
    public String getStatementId(String suffix) {
        String shortName = getEntityClass().getSimpleName();
        shortName = shortName.substring(0, 1).toLowerCase() + shortName.substring(1);
        return shortName + suffix;
    }

    @Override
    public List<E> findList(Map<String, Object> args) {
    	return getSqlSession().selectList(getStatementId(SUFFIX_FIND_LIST),args);
    }
    
    public int save(E entity) {
        return getSqlSession().insert(getStatementId(SUFFIX_INSERT), entity);
    }

    public void deleteById(Integer id) {
        delete(getStatementId(SUFFIX_DELETE_BY_ID), id);
    }
     
    public void deleteByEntity(E entity){
    	getSqlSession().delete(getStatementId(SUFFIX_DELETE_BY_ENTITY), entity);
    }
    
    public void delete(String statement, Object parameter) {
        getSqlSession().delete(statement, parameter);
    }

    public void update(E entity) {
        getSqlSession().update(getStatementId(SUFFIX_UPDATE), entity);
    }

    public void update(String statementName, Object parameterObject) {
        getSqlSession().update(statementName, parameterObject);
    }
    
    @Override
    public void updateByMap(Map<String, Object> map){
    	getSqlSession().update(getStatementId(SUFFIX_UPDATE_BY_MAP), map);
    }
    
    @SuppressWarnings( "unchecked" )
    public E findById(Object id) {
        return (E) getSqlSession().selectOne(getStatementId(SUFFIX_FIND_BY_ID), id);
    }

    public List<E> findAll(Object args) {
        return getSqlSession().selectList(getStatementId(SUFFIX_FIND_ALL), args);
    }

    @SuppressWarnings( "unchecked" )
    private E queryForObject(String statement, Object parameter) {
        return (E) getSqlSession().selectOne(statement, parameter);
    }

    public List<E> queryForList(String statement) {
        return getSqlSession().selectList(statement);
    }

    public Integer count(String statement, Object parameter) {
        return (Integer) getSqlSession().selectOne(statement, parameter);
    }

    public Long getSequenceId(String sequence) {
        return (Long) queryForObject("database.getSequence", sequence);
    }

    @Override
    public Map<String, Object> getPageInfo(Map<String, Object> args) {
    	Map<String, Object> pageInfo = new HashMap<String, Object>();
    	pageInfo.put("total",count(getStatementId(SUFFIX_COUNT), args));
    	int page = Integer.parseInt(Objects.toString(args.get("page"), "1"));
    	int rows = Integer.parseInt(Objects.toString(args.get("rows"), "20"));
    	args.put("rowStart",(page-1)*rows);
    	args.put("rowEnd",rows);
    	pageInfo.put("rows",findList(args));
    	return pageInfo;
    }
    
    @Override
    public Integer count(Map<String, Object> args) {
    	return this.getSqlSession().selectOne(getStatementId(SUFFIX_COUNT), args);
    }
}
