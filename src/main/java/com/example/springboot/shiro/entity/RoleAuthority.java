package com.example.springboot.shiro.entity;

import javax.persistence.Table;

@Table(name = "t_role_authority")
public class RoleAuthority {
    private Long roleId;
    private Long authorityId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }
}
