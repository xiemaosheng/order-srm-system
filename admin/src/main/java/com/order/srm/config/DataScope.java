package com.order.srm.config;

import com.order.srm.modules.system.domain.Dept;
import com.order.srm.modules.system.service.DeptService;
import com.order.srm.modules.system.service.RoleService;
import com.order.srm.modules.system.service.UserService;
import com.order.srm.modules.system.service.dto.RoleSmallDto;
import com.order.srm.modules.system.service.dto.UserDto;
import com.order.srm.utils.SecurityUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 数据权限配置
 *
 * @date 2019-4-1
 */
@Component
public class DataScope {

    private final String[] scopeType = {"全部", "本级", "自定义"};

    private final UserService userService;

    private final RoleService roleService;

    private final DeptService deptService;

    public DataScope(UserService userService, RoleService roleService, DeptService deptService) {
        this.userService = userService;
        this.roleService = roleService;
        this.deptService = deptService;
    }

    public Long getCompanyId() {
        UserDto user = userService.findByName(SecurityUtils.getUsername());
        return user.getCompany().getId();
    }

    public Long getLoginUserId() {
        UserDto user = userService.findByName(SecurityUtils.getUsername());
        return user.getId();
    }

    public UserDto getUser() {
        UserDto user = userService.findByName(SecurityUtils.getUsername());
        return user;
    }

    public Set<Long> getDeptIds() {

        UserDto user = userService.findByName(SecurityUtils.getUsername());

        // 用于存储部门id
        Set<Long> deptIds = new HashSet<>();

        // 查询用户角色
        List<RoleSmallDto> roleSet = roleService.findByUsersId(user.getId());

        for (RoleSmallDto role : roleSet) {

            if (scopeType[0].equals(role.getDataScope())) {
                return new HashSet<>();
            }

            // 存储本级的数据权限
            if (scopeType[1].equals(role.getDataScope())) {
                deptIds.add(user.getDept().getId());
            }

            // 存储自定义的数据权限
            if (scopeType[2].equals(role.getDataScope())) {
                Set<Dept> depts = deptService.findByRoleIds(role.getId());
                for (Dept dept : depts) {
                    deptIds.add(dept.getId());
                    List<Dept> deptChildren = deptService.findByPid(dept.getId());
                    if (deptChildren != null && deptChildren.size() != 0) {
                        deptIds.addAll(getDeptChildren(deptChildren));
                    }
                }
            }
        }
        return deptIds;
    }


    public List<Long> getDeptChildren(List<Dept> deptList) {
        List<Long> list = new ArrayList<>();
        deptList.forEach(dept -> {
                    if (dept != null && dept.getEnabled()) {
                        List<Dept> depts = deptService.findByPid(dept.getId());
                        if (deptList.size() != 0) {
                            list.addAll(getDeptChildren(depts));
                        }
                        list.add(dept.getId());
                    }
                }
        );
        return list;
    }
}
