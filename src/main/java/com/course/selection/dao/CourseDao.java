package com.course.selection.dao;

import java.util.List;

import com.course.selection.common.base.Dao;
import com.course.selection.entity.Course;
import com.course.selection.entity.vo.SelectedCourse;

public interface CourseDao extends Dao<Course> {

	Integer totalNumIncreaseWithVersion(Course course);

	Integer totalNumDecreaseWithVersion(Course course);

	List<SelectedCourse> findListByUserId(Integer userId);

	List<Course> findListByCourseIds(List<Integer> courseIds);
}
