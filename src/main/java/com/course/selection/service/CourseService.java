package com.course.selection.service;

import java.util.List;

import com.course.selection.common.base.Service;
import com.course.selection.entity.Course;
import com.course.selection.entity.vo.SelectedCourse;

public interface CourseService extends Service<Course>{

	List<SelectedCourse> findListByUserId(Integer userId);
	
	String selectCourse(List<Integer> compulsoryCourseIds, List<Integer> additionalCourseIds);
	
	void disselectCourse(List<Integer> disselectCourseIds);
	
	List<Course> findListByCourseCode(Integer courseCode);
}
