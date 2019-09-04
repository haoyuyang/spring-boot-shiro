package com.example.springboot.shiro.service;

import com.example.springboot.shiro.entity.Role;
import com.example.springboot.shiro.vo.PageResponse;
import com.example.springboot.shiro.vo.req.AddOrUpdateRoleReqVO;
import com.example.springboot.shiro.vo.req.QueryRolesReqVO;
import com.example.springboot.shiro.vo.req.UpdateRoleReqVO;

import java.util.List;

public interface RoleService {
    List<Role> list();

    Void update(UpdateRoleReqVO vo);

    PageResponse<List<Role>> getRolesByPage(QueryRolesReqVO vo);

    void addRole(AddOrUpdateRoleReqVO vo);
}
