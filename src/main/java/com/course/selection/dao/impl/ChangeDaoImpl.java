package com.course.selection.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.course.selection.common.base.DaoSupport;
import com.course.selection.dao.ChangeDao;
import com.course.selection.entity.Change;

@Repository
public class ChangeDaoImpl extends DaoSupport<Change> implements ChangeDao{

	@Override
	public List<Change> findListByUserId(Integer userId) {
		List<Change> list = getSqlSession().selectList(getStatementId(".findListByUserId"),userId);
		return list;
	}

	@Override
	public List<Change> findListByMap(Map<String,Object> map) {
		List<Change> list = getSqlSession().selectList(getStatementId(".findListByMap"),map);
		return list;
	}
	
}
