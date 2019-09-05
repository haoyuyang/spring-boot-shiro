package com.example.springboot.shiro.mapper;

import com.example.springboot.shiro.entity.Authority;
import com.example.springboot.shiro.entity.Role;
import com.example.springboot.shiro.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

@Repository
public interface RoleMapper extends Mapper<Role>, MySqlMapper<Role> {
    Role findRoleById(Long roleId);

    List<Role> findAllRoles(@Param("status") Short status, @Param("keyword") String keyword);

    void updateById(Role role);

    void deleteRoleAuthorities(Long roleId);

    Role findRoleByIdWithStatus(@Param("roleId") Long roleId, @Param("status") Short status);

    void updateStatus(@Param("id") Long id, @Param("status") Short status);

    Integer countRoleUser(Long id);
}
