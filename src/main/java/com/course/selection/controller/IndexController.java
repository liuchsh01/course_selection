package com.course.selection.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.course.selection.entity.College;
import com.course.selection.service.CollegeService;

@Controller
@RequestMapping(value="/index/")
public class IndexController {
	
	@Autowired
	CollegeService collegeService;
	
	@RequestMapping(value="index.do")
	public ModelAndView index(){
		return new ModelAndView("index/index");
	}
	
	@RequestMapping(value="college.do")
	public ModelAndView college(){
		List<College> list = collegeService.findList(new HashMap<String, Object>());
		return new ModelAndView("index/college", "colleges", list);
	}
	
	@RequestMapping(value="result.do")
	public ModelAndView result(){
		return new ModelAndView("index/result");
	}
	
	@RequestMapping(value="info.do")
	public ModelAndView info(){
		return new ModelAndView("index/info");
	}
	
	@RequestMapping(value="button.do")
	public ModelAndView button(){
		return new ModelAndView("index/button");
	}
}
