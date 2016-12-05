package com.course.selection.service;

import java.util.List;

import com.course.selection.common.base.Service;
import com.course.selection.entity.Course;
import com.course.selection.entity.vo.SelectedCourse;

public interface CourseService extends Service<Course>{

	Integer updateWithVersion(Course course);

	List<SelectedCourse> findListByUserId(Integer userId);
}
