package com.course.selection.dao.impl;

import org.springframework.stereotype.Repository;

import com.course.selection.common.base.DaoSupport;
import com.course.selection.dao.CourseDao;
import com.course.selection.entity.Course;

@Repository
public class CourseDaoImpl extends DaoSupport<Course> implements CourseDao {
	
	@Override
	public Integer updateWithVersion(Course course) {
		Integer result = getSqlSession().update(getStatementId(".updateWithVersion"), course);
		return result;
	}
}
