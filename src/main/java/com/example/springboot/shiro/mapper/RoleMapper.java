package com.example.springboot.shiro.mapper;

import com.example.springboot.shiro.entity.Role;
import com.example.springboot.shiro.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper {
    Role findRoleById(Long roleId);

    List<Role> findAllRoles(@Param("status") Short status, @Param("keyword") String keyword);

    void updateById(Role role);

    void deleteRoleAuthorities(Long roleId);

    Role findRoleByIdWithStatus(@Param("roleId") Long roleId, @Param("status") Short status);
}
