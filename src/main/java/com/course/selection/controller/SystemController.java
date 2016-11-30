package com.course.selection.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.course.selection.common.util.RandomValidateCode;
import com.course.selection.entity.User;
import com.course.selection.service.UserService;

@Controller
@RequestMapping(value="/system/")
public class SystemController {

	private final static String CODE_KEY = "login_image_code";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession httpSession;
	
	@RequestMapping(value="jumpLogin.do")
	public ModelAndView jumpLogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		return new ModelAndView("system/login");
	}
	
	@RequestMapping("/getCodeImg.do")
	@ResponseBody
    public String getCodeImg(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        httpServletResponse.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
        httpServletResponse.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
        httpServletResponse.setHeader("Cache-Control", "no-cache");
        httpServletResponse.setHeader("Set-Cookie", "name=value; HttpOnly");//设置HttpOnly属性,防止Xss攻击
        httpServletResponse.setDateHeader("Expire", 0);
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        try {
            randomValidateCode.getRandcode(httpServletRequest, httpServletResponse,CODE_KEY);// 输出图片方法
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
	
	@RequestMapping(value="login.do")
	public ModelAndView login(User user, String code){
		String sessionCode = httpSession.getAttribute(CODE_KEY) == null ? "" : (String) httpSession.getAttribute(CODE_KEY);
		if(code == null || "".equals(code) || !code.equals(sessionCode.toLowerCase())){
			return new ModelAndView("system/login", "msg", "验证码错误");
		}
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