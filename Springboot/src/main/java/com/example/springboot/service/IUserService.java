package com.example.springboot.service;

import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.User;

import java.util.List;

//接口
public interface IUserService {
    List<User>list();

    Object page(UserPageRequest userPageRequest);
}
