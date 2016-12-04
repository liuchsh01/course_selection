package com.course.selection.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.course.selection.entity.Course;
import com.course.selection.service.CourseService;

@Controller
@RequestMapping(value="/search/")
public class SearchController {

	@Autowired
	private CourseService courseService;
	
	@RequestMapping(value="college.do")
	public ModelAndView college(String id, HttpServletRequest request){
		Map<String, Object> params = new HashMap<>();
		params.put("collegeId", id);
		List<Course> list = courseService.findList(params);
		return new ModelAndView("search/search", "courses", list);
	}
	
	@RequestMapping(value="condition.do")
	public ModelAndView condition(Integer searchType, String searchText){
		if(searchType == null || searchType > 6 || searchType < 1 || searchText == null || "".equals(searchText)){
			return new ModelAndView("index/info");
		}
		Map<String, Object> params = new HashMap<>();
		switch (searchType) {
		case 2:
			params.put("courseName", searchText);
			break;
		case 3:
			params.put("teacher", searchText);
			break;
		case 4:
			params.put("majorChooser", searchText);
			break;
		case 5://剩余学位

		case 6://上课时间
			
		default:
			params.put("courseCode", searchText);
			break;
		}
		List<Course> list = courseService.findList(params);
		return new ModelAndView("search/search", "courses", list);
	}
}
