package com.example.springboot.shiro.service;

import com.example.springboot.shiro.entity.User;
import com.example.springboot.shiro.vo.BaseResponse;
import com.example.springboot.shiro.vo.PageResponse;
import com.example.springboot.shiro.vo.req.QueryUserListReqVO;
import com.example.springboot.shiro.vo.req.UserLoginReqVO;

import java.util.List;

public interface UserService {
    BaseResponse login(UserLoginReqVO vo);

    List<User> list();

    Void logout();

    PageResponse<List<User>> findUsersByCondition(QueryUserListReqVO vo);
}
