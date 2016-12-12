package com.course.selection.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.selection.common.base.ServiceSupport;
import com.course.selection.dao.CourseDao;
import com.course.selection.entity.Course;
import com.course.selection.entity.vo.SelectedCourse;
import com.course.selection.service.CourseService;

@Service
public class CourseServiceImpl extends ServiceSupport<Course> implements CourseService {

	@Autowired
	private CourseDao courseDao;
	
	@Override
	public Integer totalNumIncreaseWithVersion(Course course) {
		return courseDao.totalNumIncreaseWithVersion(course);
	}

	@Override
	public Integer totalNumDecreaseWithVersion(Course course) {
		return courseDao.totalNumDecreaseWithVersion(course);
	}

	@Override
	public List<SelectedCourse> findListByUserId(Integer userId) {
		return courseDao.findListByUserId(userId);
	}

	@Override
	public List<SelectedCourse> findListByCourseIds(List<Integer> courseIds) {
		return courseDao.findListByCourseIds(courseIds);
	}
}
