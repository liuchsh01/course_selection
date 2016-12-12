package com.course.selection.service;

import java.util.List;

import com.course.selection.common.base.Service;
import com.course.selection.entity.Selection;

public interface SelectionService extends Service<Selection> {

	public int[][] buildTimetable(List<Selection> myCourses);		
	
	public String changeDatabase(Integer out_courseId, Integer in_courseId, Integer out_userId);
}
