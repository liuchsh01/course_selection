package com.course.selection.dao;

import java.util.List;

import com.course.selection.common.base.Dao;
import com.course.selection.entity.Course;
import com.course.selection.entity.vo.SelectedCourse;

public interface CourseDao extends Dao<Course> {

	Integer updateWithVersion(Course course);

	List<SelectedCourse> findListByUserId(Integer userId);
}
