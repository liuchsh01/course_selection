package com.course.selection.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.course.selection.entity.Course;
import com.course.selection.entity.Selection;
import com.course.selection.entity.TimePlace;
import com.course.selection.entity.User;
import com.course.selection.service.CourseService;
import com.course.selection.service.SelectionService;
import com.course.selection.service.TimePlaceSerive;

@Controller
@RequestMapping(value="/select/")
public class SelectController {
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private CourseService courseService;

	@Autowired
	private TimePlaceSerive timePlaceSerive;

	@Autowired
	private SelectionService selectionService;
	
	@RequestMapping(value="selectCourse.do")
	public ModelAndView selectCourse(String data){
		
		//获取 ids
		String []datas = data.split(",");
		List<Integer> compulsoryCourseIds = new ArrayList<>();
		List<Integer> additionalCourseIds = new ArrayList<>();
		getIds(datas, compulsoryCourseIds, additionalCourseIds);
		
		//获取 本人课表ids
		User currUser = (User) httpSession.getAttribute("user");
		Map<String, Object> param = new HashMap<>();
		param.put("userId", currUser.getUserId());
		List<Integer> selectedCourseIds = getSelectedIds(param);
		
		//查找是否有重复选取的课程，如果有，直接返回
		for (int i = 0; i < selectedCourseIds.size(); i++) {
			for (int j = 0; j < compulsoryCourseIds.size(); j++) {
				if(selectedCourseIds.get(i).equals(compulsoryCourseIds.get(j))){
					return new ModelAndView("select/info","msg","请勿重复选课");
				}
			}
			for (int j = 0; j < additionalCourseIds.size(); j++) {
				if(selectedCourseIds.get(i).equals(additionalCourseIds.get(j))){
					return new ModelAndView("select/info","msg","请勿重复选课");
				}
			}
		}
		
		//获取 已选课程上课时间 与 选课上课时间
		param.clear();
		param.put("collegeIds", compulsoryCourseIds);
		List<TimePlace> compulsoryTimePlaces = timePlaceSerive.findList(param);
		param.put("collegeIds", additionalCourseIds);
		List<TimePlace> additionalTimePlaces = timePlaceSerive.findList(param);
		param.put("collegeIds", selectedCourseIds);
		List<TimePlace> SelectedTimePlaces = timePlaceSerive.findList(param);
		
		//查找是否有重复的上课时间，如果有，直接返回
		for (int i = 0; i < SelectedTimePlaces.size(); i++) {
			for (int j = 0; j < compulsoryTimePlaces.size(); j++) {
				if(SelectedTimePlaces.get(i).getWeekDay().equals(compulsoryTimePlaces.get(j).getWeekDay()) &&
						SelectedTimePlaces.get(i).getClassNo().equals(compulsoryTimePlaces.get(j).getClassNo())){
					return new ModelAndView("select/info","msg","所选课程与已选课程有时间冲突");
				}
			}
			for (int j = 0; j < additionalTimePlaces.size(); j++) {
				if(SelectedTimePlaces.get(i).getWeekDay().equals(additionalTimePlaces.get(j).getWeekDay()) &&
						SelectedTimePlaces.get(i).getClassNo().equals(additionalTimePlaces.get(j).getClassNo())){
					return new ModelAndView("select/info","msg","所选课程与已选课程有时间冲突");
				}
			}
		}
		
		//乐观锁 添加选课数据
		List<String> successCourseNames = new ArrayList<>();
		List<String> failCourseNames = new ArrayList<>();
		
		param.clear();
		for (int i = 0; i < compulsoryCourseIds.size(); i++) {
			param.put("courseId", compulsoryCourseIds.get(i));
			List<Course> courselist = courseService.findList(param);
			if(courselist != null && courselist.size() == 1 && courselist.get(0).getLimitNum() > courselist.get(0).getTotalNum()){
				Integer result = courseService.updateWithVersion(courselist.get(0));
				if(result.equals(1)){
					Selection selection = new Selection();
					selection.setUserId(currUser.getUserId());
					selection.setCourseId(compulsoryCourseIds.get(i));
					selection.setNlisten(false);
					selection.setCompulsory(true);
					selectionService.save(selection);
					successCourseNames.add(courselist.get(0).getCourseName());
				}else{
					failCourseNames.add(courselist.get(0).getCourseName());
				}
			}
		}
		for (int i = 0; i < additionalCourseIds.size(); i++) {
			param.put("courseId", additionalCourseIds.get(i));
			List<Course> courselist = courseService.findList(param);
			if(courselist != null && courselist.size() == 1 && courselist.get(0).getLimitNum() > courselist.get(0).getTotalNum()){
				Integer result = courseService.updateWithVersion(courselist.get(0));
				if(result.equals(1)){
					Selection selection = new Selection();
					selection.setUserId(currUser.getUserId());
					selection.setCourseId(compulsoryCourseIds.get(i));
					selection.setNlisten(false);
					selection.setCompulsory(false);
					selectionService.save(selection);
					successCourseNames.add(courselist.get(0).getCourseName());
				}else{
					failCourseNames.add(courselist.get(0).getCourseName());
				}
			}
		}
		return new ModelAndView("redirect:/index/info.do");
	}

	private List<Integer> getSelectedIds(Map<String, Object> param) {
		List<Selection> selectedCourse = selectionService.findList(param);
		List<Integer> selectedCourseIds = new ArrayList<>();
		if(selectedCourse != null && selectedCourse.size() > 0){
			for (int i = 0; i < selectedCourse.size(); i++) {
				selectedCourseIds.add(selectedCourse.get(i).getCourseId());
			}
		}
		return selectedCourseIds;
	}

	private void getIds(String[] datas, List<Integer> compulsoryCourseIds, List<Integer> additionalCourseIds) {
		for (int i = 0; i < datas.length; i++) {
			String[] info = datas[i].split(":");
			if(info.length == 2){
				if("0".equals(info[1])){
					compulsoryCourseIds.add(Integer.parseInt(info[0]));
				}else if("1".equals(info[1])){
					additionalCourseIds.add(Integer.parseInt(info[0]));
				}
			}
		}
	}
}
