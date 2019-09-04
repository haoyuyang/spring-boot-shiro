package com.example.springboot.shiro.controller;

import com.example.springboot.shiro.entity.User;
import com.example.springboot.shiro.service.UserService;
import com.example.springboot.shiro.vo.BaseResponse;
import com.example.springboot.shiro.vo.PageResponse;
import com.example.springboot.shiro.vo.req.QueryUserListReqVO;
import com.example.springboot.shiro.vo.req.UserLoginReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public BaseResponse login(@RequestBody UserLoginReqVO vo) {
        return userService.login(vo);
    }

    @GetMapping("/list")
    public BaseResponse<List<User>> list() {
        return new BaseResponse<>(userService.list());
    }

    @PostMapping("/page")
    public PageResponse<List<User>> findUsersByCondition(@RequestBody QueryUserListReqVO vo) {
        return userService.findUsersByCondition(vo);
    }

    @PostMapping("/logout")
    public BaseResponse logout() {
        return new BaseResponse<>(userService.logout());
    }
}
