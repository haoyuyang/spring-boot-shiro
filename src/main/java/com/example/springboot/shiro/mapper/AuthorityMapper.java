package com.example.springboot.shiro.mapper;

import com.example.springboot.shiro.entity.Authority;
import com.example.springboot.shiro.entity.RoleAuthority;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityMapper {
    List<Authority> findAuthorities();

    List<Authority> findAuthoritiesByRoleId(Long roleId);

    List<Authority> findAuthoritiesByIds(List<Long> authorityIds);

    void insertAuthorities(List<RoleAuthority> roleAuthorities);

    void updatePermissions(List<Authority> authorities);

    List<Authority> findAllAuthoritiesByRoleId(Long id);
}
