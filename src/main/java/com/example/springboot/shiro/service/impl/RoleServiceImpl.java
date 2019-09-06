package com.example.springboot.shiro.service.impl;

import com.example.springboot.shiro.entity.Authority;
import com.example.springboot.shiro.entity.Role;
import com.example.springboot.shiro.entity.RoleAuthority;
import com.example.springboot.shiro.mapper.AuthorityMapper;
import com.example.springboot.shiro.mapper.RoleAuthorityMapper;
import com.example.springboot.shiro.mapper.RoleMapper;
import com.example.springboot.shiro.service.RoleService;
import com.example.springboot.shiro.service.ShiroService;
import com.example.springboot.shiro.vo.BaseResponse;
import com.example.springboot.shiro.vo.PageResponse;
import com.example.springboot.shiro.vo.req.AddOrUpdateRoleReqVO;
import com.example.springboot.shiro.vo.req.QueryRolesReqVO;
import com.example.springboot.shiro.vo.req.UpdateRoleReqVO;
import com.example.springboot.shiro.vo.req.UpdateStatusReqVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.weekend.Weekend;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AuthorityMapper authorityMapper;
    @Autowired
    private ShiroService shiroService;
    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;

    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    @Override
    public List<Role> list() {
        return roleMapper.findAllRoles((short) 1, null);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponse update(UpdateRoleReqVO vo) {
        Long roleId = vo.getId();
        Role dbRole = roleMapper.findRoleById(roleId);

        if (dbRole == null) {
            return new BaseResponse<>(101, "角色不存在");
        }
        if ("admin".equalsIgnoreCase(dbRole.getRoleName())) {
            return new BaseResponse<>(102, "admin角色不允许操作");
        }

        Role role = new Role();
        BeanUtils.copyProperties(vo, role);
        roleMapper.updateById(role);

        //数据库中的权限
        List<Authority> authorities = authorityMapper.findAuthoritiesByRoleId(roleId);
        //传入的权限
        List<Authority> newAuthorities = authorityMapper.findAuthoritiesByIds(vo.getAuthorityIds());
        //需要添加的权限
        List<Authority> addAuthorities = new ArrayList<>();
        //需要更新的权限
        List<Authority> updateAuthorities = new ArrayList<>();

        for (Authority authority : newAuthorities) {
            //数据库中不包含传入的，说明是需要新增的
            if (!authorities.contains(authority)) {
                addAuthorities.add(authority);
            } else {
                updateAuthorities.add(authority);
            }
        }

        //需要删除的权限
        List<Authority> deleteAuthorities = new ArrayList<>();

        for (Authority authority : authorities) {
            if (!newAuthorities.contains(authority)) {
                deleteAuthorities.add(authority);
            }
        }

        if (addAuthorities.size() > 0) {
            //新增权限
            addPermissions(addAuthorities, role);
        }

        if (!Objects.equals(dbRole.getRoleName(), role.getRoleName()) && updateAuthorities.size() > 0) {
            //更新权限
            updatePermissions(updateAuthorities, dbRole, role);
        }

        if (deleteAuthorities.size() > 0) {
            //删除权限
            deletePermissions(deleteAuthorities, dbRole);
        }

        //删除之前roleId之前的authorityIds
        roleMapper.deleteRoleAuthorities(roleId);
        //保存roleId和authorityIds之间的关联关系
        addAuthorities(vo.getAuthorityIds(), role);
        //更新shiro权限
        shiroService.updatePermission(shiroFilterFactoryBean, roleId, false);

        return new BaseResponse();
    }

    @Override
    public PageResponse<List<Role>> getRolesByPage(QueryRolesReqVO vo) {
        Page<Role> page = PageHelper.startPage((vo.getPageNum() - 1) * vo.getPageSize(), vo.getPageSize());
        List<Role> roles = roleMapper.findAllRoles(vo.getStatus(), vo.getKeyword());
        for (Role role : roles) {
            List<Authority> authorities= authorityMapper.findAllAuthoritiesByRoleId(role.getId());
            role.setAuthorities(authorities);
        }
        return new PageResponse<>(page.getTotal(), roles);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponse addRole(AddOrUpdateRoleReqVO vo) {
        Weekend<Role> weekend = Weekend.of(Role.class);
        weekend.weekendCriteria().andEqualTo(Role::getRoleName, vo.getRoleName());
        Role dbRole = roleMapper.selectOneByExample(weekend);

        if (dbRole != null) {
            return new BaseResponse<>(105, "角色已存在");
        }

        Role role = new Role();
        BeanUtils.copyProperties(vo, role);
        role.setStatus((short) 1);
        roleMapper.insertSelective(role);

        List<RoleAuthority> roleAuthorities = new ArrayList<>();
        RoleAuthority roleAuthority;
        for (Long authorityId : vo.getAuthorityIds()) {
            roleAuthority = new RoleAuthority();
            roleAuthority.setAuthorityId(authorityId);
            roleAuthority.setRoleId(role.getId());
            roleAuthorities.add(roleAuthority);
        }
        roleAuthorityMapper.insertList(roleAuthorities);

        List<Authority> authorities = authorityMapper.findAuthoritiesByIds(vo.getAuthorityIds());
        String permission;
        String substring;
        StringBuilder sb = new StringBuilder();
        for (Authority authority : authorities) {
            permission = authority.getPermission();
            if (StringUtils.isNotEmpty(permission)) {
                substring = permission.substring(0, permission.lastIndexOf("]"));
                sb.append(substring).append(",").append(role.getRoleName()).append("]");
                authority.setPermission(sb.toString());
                sb.setLength(0);
            } else {
                permission = "roles[" + role.getRoleName() + "]";
                authority.setPermission(permission);
            }
        }
        authorityMapper.updatePermissions(authorities);

        return new BaseResponse();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponse updateStatus(UpdateStatusReqVO vo) {
        Role role = roleMapper.findRoleById(vo.getId());
        if (role == null) {
            return new BaseResponse<>(101, "角色不存在");
        }
        if ("admin".equalsIgnoreCase(role.getRoleName())) {
            return new BaseResponse<>(102, "admin角色不允许操作");
        }
        if (Objects.equals(role.getStatus(), vo.getStatus())) {
            return new BaseResponse(103, "状态相同，不允许修改");
        }
        roleMapper.updateStatus(vo.getId(), vo.getStatus());
        //更新shiro权限
        if (Objects.equals(vo.getStatus(), 0)) {
            shiroService.updatePermission(shiroFilterFactoryBean, vo.getId(), true);
        }
        return new BaseResponse();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponse delete(Long id) {
        Role role = roleMapper.findRoleById(id);
        if (role == null) {
            return new BaseResponse<>(101, "角色不存在");
        }
        if ("admin".equalsIgnoreCase(role.getRoleName())) {
            return new BaseResponse<>(102, "admin角色不允许操作");
        }

        //检查角色是否已关联用户
        Integer count = roleMapper.countRoleUser(id);
        if (count > 0) {
            return new BaseResponse(104, "该角色已关联用户，请先解除关联");
        }
        List<Authority> authorities = authorityMapper.findAuthoritiesByRoleId(id);
        deletePermissions(authorities, role);
        roleMapper.deleteByPrimaryKey(id);
        //删除之前roleId之前的authorityIds
        roleMapper.deleteRoleAuthorities(id);
        //更新shiro权限
        shiroService.updatePermission(shiroFilterFactoryBean, id, false);
        roleMapper.deleteByPrimaryKey(id);

        return new BaseResponse();
    }

    @Override
    public Role detail(Long id) {
        Role role = roleMapper.findRoleById(id);
        if (role != null) {
            List<Authority> authorities = authorityMapper.findAllAuthoritiesByRoleId(role.getId());
            role.setAuthorities(authorities);
        }
        return role;
    }

    private void addAuthorities(List<Long> authorityIds, Role role) {
        if (authorityIds != null && authorityIds.size() > 0) {
            List<RoleAuthority> roleAuthorities = new ArrayList<>(authorityIds.size());
            RoleAuthority authority;
            for (Long authorityId : authorityIds) {
                authority = new RoleAuthority();
                authority.setAuthorityId(authorityId);
                authority.setRoleId(role.getId());
                roleAuthorities.add(authority);
            }
            authorityMapper.insertAuthorities(roleAuthorities);
        }
    }

    private void addPermissions(List<Authority> authorities, Role role) {
        if (authorities.size() > 0) {
            String roleName = role.getRoleName();
            String permission;
            String substring;
            StringBuilder sb = new StringBuilder();
            for (Authority authority : authorities) {
                permission = authority.getPermission();
                if (StringUtils.isNotEmpty(permission)) {
                    substring = permission.substring(0, permission.lastIndexOf("]"));
                    sb.append(substring).append(",").append(roleName).append("]");
                    authority.setPermission(sb.toString());
                    sb.setLength(0);
                } else {
                    permission = "roles[" + roleName + "]";
                    authority.setPermission(permission);
                }
            }
            authorityMapper.updatePermissions(authorities);
        }
    }

    private void deletePermissions(List<Authority> authorities, Role role) {
        if (authorities.size() > 0) {
            String roleName = role.getRoleName();
            String permission;
            for (Authority authority : authorities) {
                permission = authority.getPermission();
                permission = permission.replace(roleName + ",", "");
                permission = permission.replace("," + roleName, "");
                authority.setPermission(permission);
            }
            authorityMapper.updatePermissions(authorities);
        }
    }

    private void updatePermissions(List<Authority> authorities, Role oldRole, Role role) {
        if (authorities.size() > 0) {
            String roleName = role.getRoleName();
            String permission;
            for (Authority authority : authorities) {
                permission = authority.getPermission();
                permission = permission.replace(oldRole.getRoleName(), roleName);
                authority.setPermission(permission);
            }
            authorityMapper.updatePermissions(authorities);
        }
    }
}
