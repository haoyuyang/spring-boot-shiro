package com.example.springboot.shiro.service.impl;

import com.example.springboot.shiro.entity.Authority;
import com.example.springboot.shiro.mapper.AuthorityMapper;
import com.example.springboot.shiro.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: HYY
 * @date: 2019/9/4
 * @since
 */
@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityMapper authorityMapper;

    @Override
    public List<Authority> getAuthoritiesWithParent() {
        return authorityMapper.getAuthoritiesWithParent();
    }
}
