package com.course.selection.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.selection.common.base.ServiceSupport;
import com.course.selection.dao.ChangeDao;
import com.course.selection.entity.Change;
import com.course.selection.service.ChangeService;

@Service
public class ChangeServiceImpl extends ServiceSupport<Change> implements ChangeService{
	@Autowired
	ChangeDao changeDao;
	
	@Override
	public List<Change> findListByUserId(Integer userId){
		return changeDao.findListByUserId(userId);
	}

	@Override
	public List<Change> findListByMap(Map<String, Object> map) {
		return changeDao.findListByMap(map);
	}
}
