package com.example.springboot.shiro.vo.req;

/**
 * @Description:
 * @Author: HYY
 * @Date: 2019/8/23 13:36
 * @since 1.0.0
 */
public class QueryUserListReqVO extends PageReqVO {
    private Short keywordType;
    private String keyword;
    private Short status;

    public Short getKeywordType() {
        return keywordType;
    }

    public void setKeywordType(Short keywordType) {
        this.keywordType = keywordType;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
}
