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
	public Integer totalNumIncreaseWithVersion(Course course) {
		Integer result = getSqlSession().update(getStatementId(".totalNumIncreaseWithVersion"), course);
		return result;
	}

	@Override
	public Integer totalNumDecreaseWithVersion(Course course) {
		Integer result = getSqlSession().update(getStatementId(".totalNumDecreaseWithVersion"), course);
		return result;
	}

	@Override
	public List<SelectedCourse> findListByUserId(Integer userId) {
		List<SelectedCourse> list = getSqlSession().selectList(getStatementId(".findListByUserId"),userId);
		return list;
	}

	@Override
	public List<Course> findListByCourseIds(List<Integer> courseIds) {
		List<Course> list = getSqlSession().selectList(getStatementId(".findListByCourseIds"),courseIds);
		return list;
	}

	@Override
	public List<Course> findListByCourseCode(Integer courseCode) {
		List<Course> list = getSqlSession().selectList(getStatementId(".findListByCourseCode"),courseCode);
		return list;
	}
}
