package com.course.selection.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.course.selection.entity.User;
import com.course.selection.service.UserService;

@Controller
@RequestMapping(value="/system/")
public class SystemController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession httpSession;
	
	@RequestMapping(value="jumpLogin.do")
	public ModelAndView jumpLogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		return new ModelAndView("system/login");
	}
	
	@RequestMapping(value="login.do")
	public ModelAndView login(User user){
		String msg = userService.login(user);
		if(msg != null && !"".equals(msg)){
			return new ModelAndView("system/login", "msg", msg);
		}
		return new ModelAndView("redirect:/index/index.do");
	}
	
	@RequestMapping(value="checkOut.do")
	public ModelAndView checkOut(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		httpSession.removeAttribute("user");
		return new ModelAndView("redirect:/system/jumpLogin.do");
	}
}