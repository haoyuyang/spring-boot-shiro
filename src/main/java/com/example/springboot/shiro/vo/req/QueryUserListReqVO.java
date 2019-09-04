package com.example.springboot.shiro.vo.req;

/**
 * @Description:
 * @Author: HYY
 * @Date: 2019/8/23 13:36
 * @since 1.0.0
 */
public class QueryUserListReqVO extends PageReqVO {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
