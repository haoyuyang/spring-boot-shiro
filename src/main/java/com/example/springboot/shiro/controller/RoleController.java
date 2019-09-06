package com.example.springboot.shiro.controller;

import com.example.springboot.shiro.annotation.Token;
import com.example.springboot.shiro.entity.Role;
import com.example.springboot.shiro.service.RoleService;
import com.example.springboot.shiro.vo.BaseResponse;
import com.example.springboot.shiro.vo.PageResponse;
import com.example.springboot.shiro.vo.req.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/list")
    public BaseResponse<List<Role>> list() {
        return new BaseResponse<>(roleService.list());
    }

    @PostMapping("/update")
    @Token("update")
    public BaseResponse update(@RequestBody UpdateRoleReqVO vo) {
        return roleService.update(vo);
    }

    @PostMapping("/page")
    public PageResponse<List<Role>> getRolesByPage(@RequestBody QueryRolesReqVO vo) {
        return roleService.getRolesByPage(vo);
    }

    @PostMapping("/add")
    @Token("add")
    public BaseResponse addRole(@RequestBody AddOrUpdateRoleReqVO vo) {
        return roleService.addRole(vo);
    }

    @PostMapping("/updateStatus")
    @Token("update")
    public BaseResponse updateStatus(@RequestBody UpdateStatusReqVO vo) {
        return roleService.updateStatus(vo);
    }

    @PostMapping("/delete")
    public BaseResponse delete(@RequestBody IdReqVO vo) {
        return roleService.delete(vo.getId());
    }

    @PostMapping("/detail")
    public BaseResponse<Role> detail(@RequestBody IdReqVO vo) {
        return new BaseResponse<>(roleService.detail(vo.getId()));
    }
}
