package com.example.springboot.shiro.vo.req;

/**
 * @description:
 * @author: HYY
 * @date: 2019/9/4
 * @since
 */
public class QueryRolesReqVO extends PageReqVO {
    private Short status;
    private String keyword;

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
