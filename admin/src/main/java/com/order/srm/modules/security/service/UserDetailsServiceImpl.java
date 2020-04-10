package com.order.srm.modules.security.service;

import com.order.srm.exception.BadRequestException;
import com.order.srm.modules.security.security.vo.JwtUser;
import com.order.srm.modules.system.service.CompanyService;
import com.order.srm.modules.system.service.RoleService;
import com.order.srm.modules.system.service.UserService;
import com.order.srm.modules.system.service.dto.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

/**
 * @date 2018-11-22
 */
@Service("userDetailsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CompanyService companyService;

    private final UserService userService;

    private final RoleService roleService;

    public UserDetailsServiceImpl(UserService userService, RoleService roleService, CompanyService companyService) {
        this.companyService = companyService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        String arr[] = username.split("#");
        if (ObjectUtils.isEmpty(arr) || arr.length <= 1) {
            throw new BadRequestException("用户名格式不对");
        }
        String name = arr[0];
        String code = arr[1];
        CompanyDto companyDto = companyService.findByCode(code);
        if (ObjectUtils.isEmpty(companyDto)) {
            throw new BadRequestException("公司不存在！");
        }
        UserDto user = userService.findByNameAndCode(name, code);
        if (user == null) {
            throw new BadRequestException("账号不存在");
        } else {
            if (!user.getEnabled()) {
                throw new BadRequestException("账号未激活");
            }
            return createJwtUser(user);
        }
    }

    private UserDetails createJwtUser(UserDto user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getNickName(),
                user.getSex(),
                user.getPassword(),
                user.getAvatar(),
                user.getEmail(),
                user.getPhone(),
                Optional.ofNullable(user.getCompany()).map(CompanySmallDto::getName).orElse(null),
                Optional.ofNullable(user.getDept()).map(DeptSmallDto::getName).orElse(null),
                Optional.ofNullable(user.getJob()).map(JobSmallDto::getName).orElse(null),
                roleService.mapToGrantedAuthorities(user),
                user.getEnabled(),
                user.getCreateTime(),
                user.getLastPasswordResetTime()
        );
    }
}
