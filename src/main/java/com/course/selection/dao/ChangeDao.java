package com.course.selection.dao;

import java.util.List;
import java.util.Map;

import com.course.selection.common.base.Dao;
import com.course.selection.entity.Change;

public interface ChangeDao extends Dao<Change>{
	List<Change> findListByUserId(Integer userId);
	List<Change> findListByMap(Map<String, Object> map);
}
