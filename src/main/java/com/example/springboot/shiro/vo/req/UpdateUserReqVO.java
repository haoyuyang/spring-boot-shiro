package com.example.springboot.shiro.vo.req;

import javax.validation.constraints.NotNull;

public class UpdateUserReqVO extends AddUserReqVO {
    @NotNull(message = "用户id不能为空")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
