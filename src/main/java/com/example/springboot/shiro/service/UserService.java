package com.example.springboot.shiro.service;

import com.example.springboot.shiro.entity.User;
import com.example.springboot.shiro.vo.BaseResponse;
import com.example.springboot.shiro.vo.PageResponse;
import com.example.springboot.shiro.vo.req.AddUserReqVO;
import com.example.springboot.shiro.vo.req.QueryUserListReqVO;
import com.example.springboot.shiro.vo.req.UpdateUserReqVO;
import com.example.springboot.shiro.vo.req.UserLoginReqVO;

import java.util.List;

public interface UserService {
    BaseResponse login(UserLoginReqVO vo);

    List<User> list();

    Void logout();

    PageResponse<List<User>> findUsersByCondition(QueryUserListReqVO vo);

    BaseResponse add(AddUserReqVO vo);

    BaseResponse delete(Long id);

    BaseResponse updateStatus(Long id, Short status);

    BaseResponse<User> detail(Long id);

    BaseResponse update(UpdateUserReqVO vo);
}
