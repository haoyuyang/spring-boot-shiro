package com.example.springboot.shiro.vo.req;

/**
 * @Description:
 * @Author: HYY
 * @Date: 2019/8/22 20:16
 * @since 1.0.0
 */
public class PageReqVO {
    private Integer pageNum;
    private Integer pageSize;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
