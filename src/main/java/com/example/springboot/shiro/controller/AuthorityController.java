package com.example.springboot.shiro.controller;

import com.example.springboot.shiro.entity.Authority;
import com.example.springboot.shiro.service.AuthorityService;
import com.example.springboot.shiro.vo.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @author: HYY
 * @date: 2019/9/4
 * @since
 */
@RestController
@RequestMapping("/authority")
public class AuthorityController {

    @Autowired
    private AuthorityService authorityService;

    @PostMapping("/list")
    public BaseResponse<List<Authority>> getAuthorities() {
        return new BaseResponse<>(authorityService.getAuthoritiesWithParent());
    }
}
