package com.example.springboot.shiro.service;

import com.example.springboot.shiro.entity.Authority;

import java.util.List;

/**
 * @description:
 * @author: HYY
 * @date: 2019/9/4
 * @since
 */
public interface AuthorityService {
    List<Authority> getAuthoritiesWithParent();
}
