package org.macula.engine.mybatis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.macula.engine.mybatis.entity.User;
import org.macula.engine.mybatis.mapper.UserMapper;
import org.macula.engine.mybatis.service.UserService;

import org.springframework.stereotype.Service;

/**
 * <p>
 * <b>UserServiceImpl</b> User Service Implement
 * </p>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
