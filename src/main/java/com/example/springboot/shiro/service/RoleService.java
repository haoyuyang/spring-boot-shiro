package com.example.springboot.shiro.service;

import com.example.springboot.shiro.entity.Role;
import com.example.springboot.shiro.vo.BaseResponse;
import com.example.springboot.shiro.vo.PageResponse;
import com.example.springboot.shiro.vo.req.AddOrUpdateRoleReqVO;
import com.example.springboot.shiro.vo.req.QueryRolesReqVO;
import com.example.springboot.shiro.vo.req.UpdateRoleReqVO;
import com.example.springboot.shiro.vo.req.UpdateStatusReqVO;

import java.util.List;

public interface RoleService {
    List<Role> list();

    BaseResponse update(UpdateRoleReqVO vo);

    PageResponse<List<Role>> getRolesByPage(QueryRolesReqVO vo);

    BaseResponse addRole(AddOrUpdateRoleReqVO vo);

    BaseResponse updateStatus(UpdateStatusReqVO vo);

    BaseResponse delete(Long id);

    Role detail(Long id);
}
