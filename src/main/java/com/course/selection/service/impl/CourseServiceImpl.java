package com.course.selection.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.course.selection.common.base.ServiceSupport;
import com.course.selection.dao.CourseDao;
import com.course.selection.dao.SelectionDao;
import com.course.selection.dao.TimePlaceDao;
import com.course.selection.entity.Course;
import com.course.selection.entity.Selection;
import com.course.selection.entity.TimePlace;
import com.course.selection.entity.User;
import com.course.selection.entity.vo.SelectedCourse;
import com.course.selection.service.CourseService;

@Service
public class CourseServiceImpl extends ServiceSupport<Course> implements CourseService {

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private SelectionDao selectionDao;

	@Autowired
	private TimePlaceDao timePlaceDao;

	@Override
	public List<SelectedCourse> findListByUserId(Integer userId) {
		return courseDao.findListByUserId(userId);
	}

	@Override
	public String selectCourse(List<Integer> compulsoryCourseIds, List<Integer> additionalCourseIds) {

		// 获取 本人课表ids
		User currUser = (User) httpSession.getAttribute("user");
		Map<String, Object> param = new HashMap<>();
		param.put("userId", currUser.getUserId());
		List<SelectedCourse> selectedCourses = courseDao.findListByUserId(currUser.getUserId());
		List<Integer> selectedCourseIds = new ArrayList<>();
		if (selectedCourses != null && selectedCourses.size() > 0) {
			for (int i = 0; i < selectedCourses.size(); i++) {
				selectedCourseIds.add(selectedCourses.get(i).getCourseId());
			}
		}

		// 查找是否有重复选取的课程，如果有，直接返回
		for (int i = 0; i < selectedCourseIds.size(); i++) {
			for (int j = 0; j < compulsoryCourseIds.size(); j++) {
				if (selectedCourseIds.get(i).equals(compulsoryCourseIds.get(j))) {
					return "请勿重复选课";
				}
			}
			for (int j = 0; j < additionalCourseIds.size(); j++) {
				if (selectedCourseIds.get(i).equals(additionalCourseIds.get(j))) {
					return "请勿重复选课";
				}
			}
		}

		List<Integer> newCourseIds = new ArrayList<>();
		newCourseIds.addAll(compulsoryCourseIds);
		newCourseIds.addAll(additionalCourseIds);

		// 计算总学分是否超出限选学分，如果学分超过限制，直接返回
		List<Course> newSelectedCourses = courseDao.findListByCourseIds(newCourseIds);
		Double totalCredit = 0.0;
		for (int i = 0; i < selectedCourses.size(); i++) {
			totalCredit += selectedCourses.get(i).getCredit();
		}
		for (int i = 0; i < newSelectedCourses.size(); i++) {
			totalCredit += newSelectedCourses.get(i).getCredit();
		}
		if (totalCredit > currUser.getLimitCredit())
			return "课程总分数超过限选学分";

		// 获取 已选课程上课时间 与 选课上课时间
		param.clear();
		List<TimePlace> newTimePlaces = new ArrayList<>();
		List<TimePlace> oldTimePlaces = new ArrayList<>();
		if (newCourseIds.size() > 0) {
			param.put("courseIds", newCourseIds);
			newTimePlaces = timePlaceDao.findList(param);
		}
		if (selectedCourseIds.size() > 0) {
			param.put("courseIds", selectedCourseIds);
			oldTimePlaces = timePlaceDao.findList(param);
		}

		// 查找是否有重复的上课时间，如果有，直接返回
		boolean[][] schedule = new boolean[7][12];
		for (int i = 0; i < newTimePlaces.size(); i++) {
			TimePlace timePlace = newTimePlaces.get(i);
			for (int j = 0; j < timePlace.getNum(); j++) {
				if(schedule[timePlace.getWeekDay()][timePlace.getClassNo() + j])
					return "所选课程之间有时间冲突";
				schedule[timePlace.getWeekDay()][timePlace.getClassNo() + j] = true;
			}
		}
		for (int i = 0; i < oldTimePlaces.size(); i++) {
			TimePlace timePlace = oldTimePlaces.get(i);
			for (int j = 0; j < timePlace.getNum(); j++) {
				if(schedule[timePlace.getWeekDay()][timePlace.getClassNo() + j])
					return "所选课程与已选课程有时间冲突";
				schedule[timePlace.getWeekDay()][timePlace.getClassNo() + j] = true;
			}
		}

		// 乐观锁 添加选课数据
		for (int i = 0; i < compulsoryCourseIds.size(); i++) {
			Integer courseId = compulsoryCourseIds.get(i);
			selectCourse(currUser.getUserId(), courseId, true);
		}
		for (int i = 0; i < additionalCourseIds.size(); i++) {
			Integer courseId = additionalCourseIds.get(i);
			selectCourse(currUser.getUserId(), courseId, false);
		}
		return null;
	}

	@Transactional
	private boolean selectCourse(Integer userId, Integer courseId, boolean compulsory) {
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("courseId", courseId);
			List<Course> courselist = courseDao.findList(param);
			if (courselist == null || courselist.size() != 1
					|| courselist.get(0).getLimitNum() <= courselist.get(0).getTotalNum())
				return false;
			Integer result = courseDao.totalNumIncreaseWithVersion(courselist.get(0));
			if (result == null || !result.equals(1))
				return false;
			Selection selection = new Selection();
			selection.setUserId(userId);
			selection.setCourseId(courseId);
			selection.setNlisten(false);
			selection.setCompulsory(compulsory);
			selectionDao.save(selection);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("选课异常");
		}
		return true;
	}

	@Override
	public void disselectCourse(List<Integer> disselectCourseIds) {

		User currUser = (User) httpSession.getAttribute("user");

		for (Integer courseId : disselectCourseIds) {
			disselectCourse(currUser.getUserId(), courseId);
		}
	}

	@Transactional
	private boolean disselectCourse(Integer userId, Integer courseId) {
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("courseId", courseId);
			List<Course> courselist = courseDao.findList(param);
			if (courselist == null || courselist.size() != 1
					|| courselist.get(0).getLimitNum() <= courselist.get(0).getTotalNum())
				return false;
			Integer result = courseDao.totalNumDecreaseWithVersion(courselist.get(0));
			if (result == null || !result.equals(1))
				return false;
			Selection selection = new Selection();
			selection.setUserId(userId);
			selection.setCourseId(courseId);
			selectionDao.deleteByEntity(selection);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("退课异常");
		}
		return true;
	}
}
