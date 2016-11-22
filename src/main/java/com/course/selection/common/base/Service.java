package com.course.selection.common.base;

import java.util.List;
import java.util.Map;


public interface Service<E> {
    
    public void save(E view);

    public void saveEntityList(List<E> list);

    public void deleteById(Integer id);

    public void update(E view);

    public void updateEntityList(List<E> list);

    public E getEntity(Object id);

    public List<E> getEntityList(Map<String, Object> args);

    public List<E> findList(Map<String, Object> args);

    public Long getSequenceId(String sequence);
    
    public Map<String,Object> getPageInfo(Map<String,Object> args);
    
    public Integer count(Map<String, Object> args);

}
