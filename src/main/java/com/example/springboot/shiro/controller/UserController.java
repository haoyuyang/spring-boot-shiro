package com.example.springboot.shiro.controller;

import com.example.springboot.shiro.annotation.Token;
import com.example.springboot.shiro.entity.User;
import com.example.springboot.shiro.service.UserService;
import com.example.springboot.shiro.util.RequestUtil;
import com.example.springboot.shiro.vo.BaseResponse;
import com.example.springboot.shiro.vo.PageResponse;
import com.example.springboot.shiro.vo.req.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 登录
     *
     * @param vo
     * @return
     */
    @PostMapping("/login")
    public BaseResponse login(@RequestBody UserLoginReqVO vo) {
        return userService.login(vo);
    }

    /**
     * 用户列表
     *
     * @return
     */
    @PostMapping("/list")
    public BaseResponse<List<User>> list() {
        return new BaseResponse<>(userService.list());
    }

    /**
     * 用户列表（分页）
     *
     * @param vo
     * @return
     */
    @PostMapping("/page")
    public PageResponse<List<User>> findUsersByCondition(@RequestBody QueryUserListReqVO vo) {
        return userService.findUsersByCondition(vo);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse logout() {
        return new BaseResponse<>(userService.logout());
    }

    /**
     * 新增用户
     *
     * @param vo
     * @return
     */
    @PostMapping("/add")
    @Token("add")
    public BaseResponse add(@RequestBody AddUserReqVO vo) {
        return userService.add(vo);
    }

    /**
     * 删除用户
     *
     * @param vo
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse delete(@RequestBody IdReqVO vo) {
        return userService.delete(vo.getId());
    }

    /**
     * 启用/禁用用户
     *
     * @param vo
     * @return
     */
    @PostMapping("/updateStatus")
    @Token("update")
    public BaseResponse updateStatus(@RequestBody UpdateStatusReqVO vo) {
        return userService.updateStatus(vo.getId(), vo.getStatus());
    }

    /**
     * 用户详情
     * @param vo
     * @return
     */
    @PostMapping("/detail")
    public BaseResponse<User> detail(@Valid  @RequestBody IdReqVO vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return RequestUtil.handleInvalidRequest(bindingResult, new BaseResponse<>());
        }
        return userService.detail(vo.getId());
    }

    @PostMapping("/update")
    @Token("update")
    public BaseResponse update(@Valid @RequestBody UpdateUserReqVO vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return RequestUtil.handleInvalidRequest(bindingResult, new BaseResponse<>());
        }
        return userService.update(vo);
    }
}
