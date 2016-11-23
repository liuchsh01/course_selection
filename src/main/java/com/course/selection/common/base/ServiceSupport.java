package com.course.selection.common.base;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;


public abstract class ServiceSupport<E> implements Service<E> {

    @Autowired
    private Dao<E> dao;

    @SuppressWarnings( "unchecked" )
    protected Class<E> getViewClass() {
        return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[1];
    }

    public void save(E view) {
        dao.save(view);
    }

    public void saveEntityList(List<E> list) {
        for (E entity : list) {
            dao.save(entity);
        }
    }

    public void deleteById(Integer id) {
        dao.deleteById(id);
    }

    public void update(E view) {
        dao.update(view);
    }

    public void updateEntityList(List<E> list) {
        for (E entity : list) {
            dao.update(entity);
        }
    }

    public E getEntity(Object id) {
        return dao.findById(id);
    }

    public List<E> getEntityList(Map<String, Object> args) {
        return dao.findAll(args);
    }

    public List<E> findList(Map<String, Object> args) {
        return dao.findList(args);
    }

    public Long getSequenceId(String sequence) {
        return dao.getSequenceId(sequence);
    }
    
    @Override
    public Map<String, Object> getPageInfo(Map<String, Object> args) {
    	return dao.getPageInfo(args);
    }
    
    @Override
    public Integer count(Map<String, Object> args) {
    	return dao.count(args);
    }

	public Dao<E> getDao() {
		return dao;
	}
}
