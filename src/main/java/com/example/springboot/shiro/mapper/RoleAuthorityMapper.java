package com.example.springboot.shiro.mapper;

import com.example.springboot.shiro.entity.RoleAuthority;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @description:
 * @author: HYY
 * @date: 2019/9/4
 * @since
 */
@Repository
public interface RoleAuthorityMapper extends Mapper<RoleAuthority>, MySqlMapper<RoleAuthority> {
}
