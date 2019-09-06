package com.example.springboot.shiro.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "t_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "role_name")
    private String roleName;
    @Column(name = "status")
    private Short status;
    @Column(name = "creator")
    private String creator;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "updater")
    private String updater;
    @Column(name = "update_time")
    private Date updateTime;
    @Transient
    private List<Integer> authorityIds;
    @Transient
    private List<Authority> authorities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<Integer> getAuthorityIds() {
        return authorityIds;
    }

    public void setAuthorityIds(List<Integer> authorityIds) {
        this.authorityIds = authorityIds;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

}
