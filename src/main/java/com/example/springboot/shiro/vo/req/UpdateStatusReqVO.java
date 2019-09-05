package com.example.springboot.shiro.vo.req;

/**
 * @description:
 * @author: HYY
 * @date: 2019/9/5
 * @since
 */
public class UpdateStatusReqVO {
    private Long id;
    private Short status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
}
