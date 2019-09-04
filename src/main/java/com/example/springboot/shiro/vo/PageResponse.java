package com.example.springboot.shiro.vo;

/**
 * @Description:
 * @Author: HYY
 * @Date: 2019/8/22 21:00
 * @since 1.0.0
 */
public class PageResponse<T> extends BaseResponse<T> {
    private Long total;
    public PageResponse(Long total, T result) {
        this.total = total;
        super.result = result;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
