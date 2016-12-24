package com.course.selection.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.course.selection.entity.Course;
import com.course.selection.entity.TimePlace;
import com.course.selection.service.CourseService;
import com.course.selection.service.TimePlaceService;

@Controller
@RequestMapping(value="/search/")
public class SearchController {

	@Autowired
	private CourseService courseService;
	
	@Autowired
	private TimePlaceService timePlaceService;
	
	@RequestMapping(value="college.do")
	public ModelAndView college(String id){
		if(id == null || "".equals(id)){
			return new ModelAndView("index/info");
		}

		Map<String, Object> params = new HashMap<>();
		params.put("collegeId", id);
		Map<String, Object> data = getData(params);
		
		return new ModelAndView("search/search", data);
	}
	
	@RequestMapping(value="condition.do")
	public ModelAndView condition(Integer searchType, String searchText){
		if(searchType == null || searchType > 6 || searchType < 1 || (!searchType.equals(5) && (searchText == null || "".equals(searchText)))){
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
			params.put("remain", "remain");
			break;
		case 6://上课时间
			
		default:
			params.put("courseCode", searchText);
			break;
		}

		Map<String, Object> data = getData(params);
		
		return new ModelAndView("search/search", data);
	}
	
	@RequestMapping(value="courseCount.do")
	public ModelAndView courseCount(String id){
		Map<String, Object> params = new HashMap<>();
		params.put("courseId", id);
		List<Course> list = courseService.findList(params);
		Course course = null;
		if(list != null && list.size() != 0){
			course = list.get(0);
		}
		return new ModelAndView("search/courseCount", "course", course);
	}

	private Map<String, Object> getData(Map<String, Object> params) {
		Map<String, Object> data = new HashMap<>();
		List<Course> courses = courseService.findList(params);
		Map<Integer, List<TimePlace>> timePlaces = getTimePlaces(courses);
		data.put("courses", courses);
		data.put("timePlaces", timePlaces);
		return data;
	}

	private Map<Integer, List<TimePlace>> getTimePlaces(List<Course> courses) {
		Map<Integer, List<TimePlace>> timePlaces = new HashMap<>();
		if(courses != null && courses.size() != 0){
			List<Integer> courseIds = new ArrayList<>();
			for (Course course : courses) {
				courseIds.add(course.getCourseId());
			}
			Map<String, Object> params = new HashMap<>();
			params.put("courseIds", courseIds);
			List<TimePlace> timePlaceList = timePlaceService.findList(params);
			if(timePlaceList != null && timePlaceList.size() != 0){
				for (TimePlace timePlace : timePlaceList) {
					List<TimePlace> list = timePlaces.get(timePlace.getCourseId());
					if(list == null){
						list = new ArrayList<>();
						timePlaces.put(timePlace.getCourseId(), list);
					}
					list.add(timePlace);
				}
			}
		}
		return timePlaces;
	}
}
