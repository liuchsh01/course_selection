package com.course.selection.service.impl;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.selection.common.base.ServiceSupport;
import com.course.selection.common.util.Sha1Util;
import com.course.selection.dao.UserDao;
import com.course.selection.entity.User;
import com.course.selection.service.UserService;

@Service
public class UserServiceImpl extends ServiceSupport<User> implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private HttpSession httpSession;
	
	@Override
	public String login(User user) {
		if(user.getStudno() != null && !"".equals(user.getStudno())){
			if(user.getPassword() != null && !"".equals(user.getPassword())){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("studno", user.getStudno());
				params.put("password", Sha1Util.getSha1(user.getPassword()));
				List<User> list = userDao.findList(params);
				if (list != null && list.size() > 0) {
					user = list.get(0);
					user.setPassword("");
					httpSession.setAttribute("user", user);
					return "";
				}
				return "学号或密码错误";
			}
			return "密码不能为空";
		}
		return "学号不能为空";
	}

}
