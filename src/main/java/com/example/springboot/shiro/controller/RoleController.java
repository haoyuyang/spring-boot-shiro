package com.example.springboot.shiro.controller;

import com.example.springboot.shiro.entity.Role;
import com.example.springboot.shiro.service.RoleService;
import com.example.springboot.shiro.vo.BaseResponse;
import com.example.springboot.shiro.vo.PageResponse;
import com.example.springboot.shiro.vo.req.AddOrUpdateRoleReqVO;
import com.example.springboot.shiro.vo.req.PageReqVO;
import com.example.springboot.shiro.vo.req.QueryRolesReqVO;
import com.example.springboot.shiro.vo.req.UpdateRoleReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public BaseResponse<List<Role>> list() {
        return new BaseResponse<>(roleService.list());
    }

    @PostMapping("/update")
    public BaseResponse update(@RequestBody UpdateRoleReqVO vo) {
        return new BaseResponse<>(roleService.update(vo));
    }

    @PostMapping("/page")
    public PageResponse<List<Role>> getRolesByPage(@RequestBody QueryRolesReqVO vo) {
        return roleService.getRolesByPage(vo);
    }

    @PostMapping("/add")
    public BaseResponse addRole(@RequestBody AddOrUpdateRoleReqVO vo) {
        roleService.addRole(vo);
        return new BaseResponse();
    }

}
