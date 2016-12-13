package com.course.selection.controller;

import java.util.List;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.course.selection.service.CourseService;

@Controller
@RequestMapping(value = "/select/")
public class SelectController {

	@Autowired
	private CourseService courseService;

	@RequestMapping(value = "selectCourse.do")
	public ModelAndView selectCourse(String data) {
		try {
			if (data == null)
				return new ModelAndView("select/info", "msg", "空操作");
			// 获取 ids
			String[] datas = data.split(",");
			List<Integer> compulsoryCourseIds = new ArrayList<>();
			List<Integer> additionalCourseIds = new ArrayList<>();
			for (int i = 0; i < datas.length; i++) {
				String[] info = datas[i].split(":");
				if (info.length == 2) {
					if ("0".equals(info[1])) {
						compulsoryCourseIds.add(Integer.parseInt(info[0]));
					} else if ("1".equals(info[1])) {
						additionalCourseIds.add(Integer.parseInt(info[0]));
					}
				}
			}
			if (compulsoryCourseIds.size() == 0 && additionalCourseIds.size() == 0)
				return new ModelAndView("select/info", "msg", "空操作");

			String result = courseService.selectCourse(compulsoryCourseIds, additionalCourseIds);
			if (result != null)
				return new ModelAndView("select/info", "msg", result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/index/result.do");
	}

	@RequestMapping(value = "disselectCourse.do")
	public ModelAndView disselectCourse(String data) {
		try {
			if (data == null)
				return new ModelAndView("select/info", "msg", "空操作");

			String[] datas = data.split(",");
			List<Integer> disselectCourseIds = new ArrayList<>();

			for (int i = 0; i < datas.length; i++)
				disselectCourseIds.add(Integer.parseInt(datas[i]));

			if (disselectCourseIds.size() != 0)
				courseService.disselectCourse(disselectCourseIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/index/result.do");
	}
}
