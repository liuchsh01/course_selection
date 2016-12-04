package com.course.selection.dao.impl;

import org.springframework.stereotype.Repository;

import com.course.selection.common.base.DaoSupport;
import com.course.selection.dao.UserDao;
import com.course.selection.entity.User;

@Repository
public class UserDaoImpl extends DaoSupport<User> implements UserDao {
}
