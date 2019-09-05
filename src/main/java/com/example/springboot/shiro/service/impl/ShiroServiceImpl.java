package com.example.springboot.shiro.service.impl;

import com.example.springboot.shiro.entity.Authority;
import com.example.springboot.shiro.entity.User;
import com.example.springboot.shiro.mapper.AuthorityMapper;
import com.example.springboot.shiro.mapper.UserMapper;
import com.example.springboot.shiro.service.ShiroService;
import com.example.springboot.shiro.util.ShiroUtil;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("shiroService")
public class ShiroServiceImpl implements ShiroService {

    @Autowired
    private AuthorityMapper authorityMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 初始化权限
     */
    @Override
    public Map<String, String> loadFilterChainDefinitions() {
        List<Authority> authorities = authorityMapper.findAuthorities();
        // 权限控制map.从数据库获取
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        if (authorities.size() > 0) {
            String uris;
            String[] uriArr;
            for (Authority authority : authorities) {
                if (StringUtils.isEmpty(authority.getPermission())) {
                    continue;
                }
                uris = authority.getUri();
                uriArr = uris.split(",");
                for (String uri : uriArr) {
                    filterChainDefinitionMap.put(uri, authority.getPermission());
                }
            }
        }
        filterChainDefinitionMap.put("/user/login", "anon");
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/user/logout", "anon");
        //拦截所有请求
        filterChainDefinitionMap.put("/**", "authc");
        return filterChainDefinitionMap;
    }

    /**
     * 在对角色进行增删改操作时，需要调用此方法进行动态刷新
     * @param shiroFilterFactoryBean
     */
    @Override
    public void updatePermission(ShiroFilterFactoryBean shiroFilterFactoryBean, Long roleId, Boolean isRemoveSession) {
        synchronized (this) {
            AbstractShiroFilter shiroFilter;
            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
            } catch (Exception e) {
                throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
            }
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
            // 清空老的权限控制
            manager.getFilterChains().clear();
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            shiroFilterFactoryBean.setFilterChainDefinitionMap(loadFilterChainDefinitions());
            // 重新构建生成
            Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim().replace(" ", "");
                manager.createChain(url, chainDefinition);
            }

            List<User> users = userMapper.findUsersByRoleId(roleId);

            if (users.size() > 0) {
                for (User user : users) {
                    ShiroUtil.kickOutUser(user.getUsername(), isRemoveSession);
                }
            }
        }
    }
}
