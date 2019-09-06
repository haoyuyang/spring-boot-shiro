package com.example.springboot.shiro.vo.req;

import com.example.springboot.shiro.vo.BaseRequest;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

public class AddUserReqVO extends BaseRequest {
    @NotNull(message = "用户名不能为空")
    private String username;
    @NotNull(message = "密码不能为空")
    private String password;
    @NotNull(message = "联系人不能为空")
    private String contacts;
    @NotNull(message = "联系电话不能为空")
    private String contactNumber;
    @Email(message = "邮箱格式不正确")
    private String email;
    @NotNull(message = "角色id不能为空")
    private Long roleId;
    private Integer status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "AddUserReqVO{" +
                "userName='" + username + '\'' +
                ", password='" + password + '\'' +
                ", contacts='" + contacts + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", email='" + email + '\'' +
                ", roleId=" + roleId +
                ", status=" + status +
                '}';
    }
}
