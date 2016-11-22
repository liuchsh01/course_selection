package com.course.selection.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/system/")
public class SystemController {

	@RequestMapping(value="jumpLogin.do")
	public String jumpLogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		return "system/login";
	}
}
