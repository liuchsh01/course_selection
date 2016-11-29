package com.course.selection.service;

import com.course.selection.common.base.Service;
import com.course.selection.entity.User;

public interface UserService extends Service<User>{

	public String login(User user);

}
