package com.example.springboot.shiro.vo.req;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: HYY
 * @date: 2019/9/5
 * @since
 */
public class IdReqVO {
    @NotNull(message = "不可为空")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
