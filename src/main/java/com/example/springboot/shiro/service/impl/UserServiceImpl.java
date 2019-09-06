package com.example.springboot.shiro.service.impl;

import com.example.springboot.shiro.entity.Role;
import com.example.springboot.shiro.entity.User;
import com.example.springboot.shiro.mapper.RoleMapper;
import com.example.springboot.shiro.mapper.UserMapper;
import com.example.springboot.shiro.service.UserService;
import com.example.springboot.shiro.util.AES256CryptUtil;
import com.example.springboot.shiro.util.JwtUtil;
import com.example.springboot.shiro.util.ShiroUtil;
import com.example.springboot.shiro.vo.BaseResponse;
import com.example.springboot.shiro.vo.PageResponse;
import com.example.springboot.shiro.vo.req.AddUserReqVO;
import com.example.springboot.shiro.vo.req.QueryUserListReqVO;
import com.example.springboot.shiro.vo.req.UpdateUserReqVO;
import com.example.springboot.shiro.vo.req.UserLoginReqVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public BaseResponse login(UserLoginReqVO vo) {
        //主体,当前状态为没有认证的状态“未认证”
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(vo.getUsername(), AES256CryptUtil.encrypt(vo.getPassword().getBytes(), "spring-boot-shiro", "spring-boot-shiro"));
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            String message;
            if (e.getMessage().length() > 13) {
                message = "用户名或密码错误";
            } else {
                message = e.getMessage();
            }
            return new BaseResponse(403, message);
        } catch (Exception e) {
            return new BaseResponse(500, "未知错误");
        }
        User user = (User) subject.getPrincipal();
        return new BaseResponse<>(JwtUtil.createJWT(String.valueOf(user.getId()), user.getUsername(), 86400000));
    }

    @Override
    public List<User> list() {
        return userMapper.findAllUsers();
    }

    @Override
    public Void logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return null;
    }

    @Override
    public PageResponse<List<User>> findUsersByCondition(QueryUserListReqVO vo) {
        Page<User> page = PageHelper.startPage((vo.getPageNum() - 1) * vo.getPageSize(), vo.getPageSize());
        List<User> userListPage = userMapper.findUsersByCondition(vo.getKeywordType(), vo.getKeyword(), vo.getStatus());
        return new PageResponse<>(page.getTotal(), userListPage);
    }

    @Override
    public BaseResponse add(AddUserReqVO vo) {
        //检查用户名是否存在
        User dbUser = userMapper.findUserByUserName(vo.getUsername());
        if (dbUser != null) {
            return new BaseResponse(201, "用户名已存在");
        }

        //校验角色是否存在
        Role role = roleMapper.findRoleById(vo.getRoleId());
        if (role == null) {
            return new BaseResponse(101, "角色不存在");
        }

        User user = new User();
        BeanUtils.copyProperties(vo, user);
        user.setPassword(AES256CryptUtil.encrypt(vo.getPassword().getBytes(), "spring-boot-shiro", "spring-boot-shiro"));
        userMapper.insertSelective(user);

        return new BaseResponse();
    }

    @Override
    public BaseResponse delete(Long id) {
        User dbUser = userMapper.selectByPrimaryKey(id);
        if (dbUser == null) {
            return new BaseResponse(202, "用户不存在");
        }
        if ("admin".equalsIgnoreCase(dbUser.getUsername())) {
            return new BaseResponse<>(203, "admin用户不允许操作");
        }
        userMapper.deleteByPrimaryKey(id);
        // 将用户踢出
        ShiroUtil.kickOutUser(dbUser.getUsername(), true);
        return new BaseResponse();
    }

    @Override
    public BaseResponse updateStatus(Long id, Short status) {
        User dbUser = userMapper.selectByPrimaryKey(id);
        if (dbUser == null) {
            return new BaseResponse(202, "用户不存在");
        }

        if ("admin".equalsIgnoreCase(dbUser.getUsername())) {
            return new BaseResponse<>(203, "admin用户不允许操作");
        }
        userMapper.updateStatusById(id, status);
        // 禁用用户则将用户踢出
        if (Objects.equals(status, 0)) {
            ShiroUtil.kickOutUser(dbUser.getUsername(), true);
        }
        return new BaseResponse();
    }

    @Override
    public BaseResponse<User> detail(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            return new BaseResponse<>(202, "用户不存在");
        }
        user.setPassword(AES256CryptUtil.decrypt(Base64.decode(user.getPassword()), "spring-boot-shiro", "spring-boot-shiro"));
        return new BaseResponse<>(user);
    }

    @Override
    public BaseResponse update(UpdateUserReqVO vo) {
        User dbUser = userMapper.selectByPrimaryKey(vo.getId());
        if (dbUser == null) {
            return new BaseResponse<>(202, "用户不存在");
        }

        if ("admin".equalsIgnoreCase(dbUser.getUsername())) {
            return new BaseResponse(203, "admin用户不允许操作");
        }

        //更新用户信息
        User user = new User();
        BeanUtils.copyProperties(vo, user);
        user.setPassword(AES256CryptUtil.encrypt(vo.getPassword(), "spring-boot-shiro", "spring-boot-shiro"));
        userMapper.updateByPrimaryKey(user);

        String oldPassword = AES256CryptUtil.decrypt(Base64.decode(dbUser.getPassword()), "spring-boot-shiro", "spring-boot-shiro");
        //如果修改了密码则删除session使用户重新登录
        boolean isRemoveSession = !Objects.equals(oldPassword, vo.getPassword());
        ShiroUtil.kickOutUser(user.getUsername(), isRemoveSession);
        return new BaseResponse();
    }

}
