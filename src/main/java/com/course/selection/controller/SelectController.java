package com.course.selection.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.course.selection.entity.Course;
import com.course.selection.entity.Selection;
import com.course.selection.entity.TimePlace;
import com.course.selection.entity.User;
import com.course.selection.entity.vo.SelectedCourse;
import com.course.selection.service.CourseService;
import com.course.selection.service.SelectionService;
import com.course.selection.service.TimePlaceService;

@Controller
@RequestMapping(value="/select/")
public class SelectController {
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private CourseService courseService;

	@Autowired
	private TimePlaceService timePlaceService;

	@Autowired
	private SelectionService selectionService;
	
	@RequestMapping(value="selectCourse.do")
	public ModelAndView selectCourse(String data){
		
		if(data == null)
			return new ModelAndView("select/info","msg","空操作");
		//获取 ids
		String []datas = data.split(",");
		List<Integer> compulsoryCourseIds = new ArrayList<>();
		List<Integer> additionalCourseIds = new ArrayList<>();
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
		if(compulsoryCourseIds.size() == 0 && additionalCourseIds.size() == 0)
			return new ModelAndView("select/info","msg","空操作");
		
		//获取 本人课表ids
		User currUser = (User) httpSession.getAttribute("user");
		Map<String, Object> param = new HashMap<>();
		param.put("userId", currUser.getUserId());
		List<SelectedCourse> selectedCourses = courseService.findListByUserId(currUser.getUserId());
		List<Integer> selectedCourseIds = new ArrayList<>();
		if(selectedCourses != null && selectedCourses.size() > 0){
			for (int i = 0; i < selectedCourses.size(); i++) {
				selectedCourseIds.add(selectedCourses.get(i).getCourseId());
			}
		}
		
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
		
		List<Integer> newCourseIds = new ArrayList<>();
		newCourseIds.addAll(compulsoryCourseIds);
		newCourseIds.addAll(additionalCourseIds);
		
		//计算总学分是否超出限选学分，如果学分超过限制，直接返回
		List<SelectedCourse> newSelectedCourses = courseService.findListByCourseIds(newCourseIds);
		Double totalCredit = 0.0;
		for (int i = 0; i < selectedCourses.size(); i++) {
			totalCredit += selectedCourses.get(i).getCredit();
		}
		for (int i = 0; i < newSelectedCourses.size(); i++) {
			totalCredit += newSelectedCourses.get(i).getCredit();
		}
		if (totalCredit > currUser.getLimitCredit())
			return new ModelAndView("select/info","msg","课程总分数超过限选学分");
		
		//获取 已选课程上课时间 与 选课上课时间
		param.clear();
		List<TimePlace> newTimePlaces = new ArrayList<>();
		List<TimePlace> oldTimePlaces = new ArrayList<>();
		if (newCourseIds.size() > 0) {
			param.put("courseIds", newCourseIds);
			newTimePlaces = timePlaceService.findList(param);
		}
		if (selectedCourseIds.size() > 0) {
			param.put("courseIds", selectedCourseIds);
			oldTimePlaces = timePlaceService.findList(param);
		}
		
		//查找是否有重复的上课时间，如果有，直接返回
		for (int i = 0; i < newTimePlaces.size(); i++) {
			for (int j = 0; j < oldTimePlaces.size(); j++) {
				if(newTimePlaces.get(i).getWeekDay().equals(oldTimePlaces.get(j).getWeekDay()) &&
						newTimePlaces.get(i).getClassNo().equals(oldTimePlaces.get(j).getClassNo())){
					return new ModelAndView("select/info","msg","所选课程与已选课程有时间冲突");
				}
			}
			for (int j = 0; j < newTimePlaces.size(); j++) {
				if(i != j && newTimePlaces.get(i).getWeekDay().equals(newTimePlaces.get(j).getWeekDay()) &&
						newTimePlaces.get(i).getClassNo().equals(newTimePlaces.get(j).getClassNo())){
					return new ModelAndView("select/info","msg","所选课程之间有时间冲突");
				}
			}
		}
		
		//乐观锁 添加选课数据
		for (int i = 0; i < compulsoryCourseIds.size(); i++) {
			Integer courseId = compulsoryCourseIds.get(i);
			selectCourse(currUser.getUserId(), courseId, true);
		}
		for (int i = 0; i < additionalCourseIds.size(); i++) {
			Integer courseId = additionalCourseIds.get(i);
			selectCourse(currUser.getUserId(), courseId, false);
		}
		return new ModelAndView("redirect:/index/info.do");
	}

	@Transactional
	private boolean selectCourse(Integer userId, Integer courseId, boolean compulsory) {
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("courseId", courseId);
			List<Course> courselist = courseService.findList(param);
			if (courselist == null || courselist.size() != 1
					|| courselist.get(0).getLimitNum() <= courselist.get(0).getTotalNum())
				return false;
			Integer result = courseService.totalNumIncreaseWithVersion(courselist.get(0));
			if (result == null || !result.equals(1))
				return false;
			Selection selection = new Selection();
			selection.setUserId(userId);
			selection.setCourseId(courseId);
			selection.setNlisten(false);
			selection.setCompulsory(compulsory);
			selectionService.save(selection);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	@RequestMapping(value="disselectCourse.do")
	public ModelAndView disselectCourse(String data){

		if(data == null)
			return new ModelAndView("select/info","msg","空操作");

		String []datas = data.split(",");
		User currUser = (User) httpSession.getAttribute("user");
		
		for (int i = 0; i < datas.length; i++)
			disselectCourse(currUser.getUserId(), Integer.parseInt(datas[i]));

		return new ModelAndView("redirect:/index/info.do");
	}

	@Transactional
	private boolean disselectCourse(Integer userId, Integer courseId) {
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("courseId", courseId);
			List<Course> courselist = courseService.findList(param);
			if (courselist == null || courselist.size() != 1
					|| courselist.get(0).getLimitNum() <= courselist.get(0).getTotalNum())
				return false;
			Integer result = courseService.totalNumDecreaseWithVersion(courselist.get(0));
			if (result == null || !result.equals(1))
				return false;
			Selection selection = new Selection();
			selection.setUserId(userId);
			selection.setCourseId(courseId);
			selectionService.deleteByEntity(selection);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
