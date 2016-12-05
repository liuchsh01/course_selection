package com.course.selection.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.course.selection.common.base.DaoSupport;
import com.course.selection.dao.CourseDao;
import com.course.selection.entity.Course;
import com.course.selection.entity.vo.SelectedCourse;

@Repository
public class CourseDaoImpl extends DaoSupport<Course> implements CourseDao {
	
	@Override
	public Integer updateWithVersion(Course course) {
		Integer result = getSqlSession().update(getStatementId(".updateWithVersion"), course);
		return result;
	}

	@Override
	public List<SelectedCourse> findListByUserId(Integer userId) {
		List<SelectedCourse> list = getSqlSession().selectList(getStatementId(".findListByUserId"),userId);
		return list;
	}
}
