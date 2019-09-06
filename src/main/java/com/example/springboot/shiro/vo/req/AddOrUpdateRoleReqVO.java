package com.example.springboot.shiro.vo.req;

import com.example.springboot.shiro.vo.BaseRequest;

import java.util.List;

/**
 * @description:
 * @author: HYY
 * @date: 2019/9/4
 * @since
 */
public class AddOrUpdateRoleReqVO extends BaseRequest {
    private String roleName;
    private List<Long> authorityIds;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Long> getAuthorityIds() {
        return authorityIds;
    }

    public void setAuthorityIds(List<Long> authorityIds) {
        this.authorityIds = authorityIds;
    }
}
