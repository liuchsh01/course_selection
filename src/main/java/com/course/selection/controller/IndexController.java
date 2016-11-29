package com.course.selection.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/index/")
public class IndexController {
	
	@RequestMapping(value="index.do")
	public ModelAndView index(){
		return new ModelAndView("index/index");
	}
	
	@RequestMapping(value="college.do")
	public ModelAndView college(){
		return new ModelAndView("index/college");
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
