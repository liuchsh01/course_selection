package com.course.selection.service;

import java.util.List;
import java.util.Map;

import com.course.selection.common.base.Service;
import com.course.selection.entity.Change;

public interface ChangeService extends Service<Change>{
	public List<Change> findListByUserId(Integer userId);
	public List<Change> findListByMap(Map<String, Object> map);
}
