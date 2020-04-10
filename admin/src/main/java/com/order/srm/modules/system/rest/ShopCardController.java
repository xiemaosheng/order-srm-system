package com.order.srm.modules.system.rest;

import com.order.srm.config.DataScope;
import com.order.srm.exception.BadRequestException;
import com.order.srm.log.Log;
import com.order.srm.modules.system.domain.Order;
import com.order.srm.modules.system.domain.ShopCard;
import com.order.srm.modules.system.service.OrderService;
import com.order.srm.modules.system.service.ShopCardService;
import com.order.srm.modules.system.service.dto.OrderQueryCriteria;
import com.order.srm.modules.system.service.dto.ShopCardQueryCriteria;
import com.order.srm.modules.system.service.dto.UserDto;
import com.order.srm.utils.ThrowableUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @date 2019-03-25
 */
@RestController
@Api(tags = "购物卡分配管理")
@RequestMapping("/api/shopCard/allocation")
public class ShopCardController {

    private final ShopCardService shopCardService;

    private final DataScope dataScope;

    private static final String ENTITY_NAME = "shopCard";

    public ShopCardController(ShopCardService shopCardService, DataScope dataScope) {
        this.shopCardService = shopCardService;
        this.dataScope = dataScope;
    }

    @Log("导出购物卡数据")
    @ApiOperation("导出购物卡数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('shopCard:list')")
    public void download(HttpServletResponse response, ShopCardQueryCriteria criteria) throws IOException {
        shopCardService.download(shopCardService.queryAll(criteria), response);
    }

    @Log("查询购物卡数据")
    @ApiOperation("查询购物卡数据")
    @GetMapping
    @PreAuthorize("@el.check('user:list','shopCard:list')")
    public ResponseEntity<Object> getOrderAddress(ShopCardQueryCriteria criteria, Pageable pageable) {
        // 数据权限
        criteria.setCompanyId(dataScope.getCompanyId());
        return new ResponseEntity<>(shopCardService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("新增购物卡数据")
    @ApiOperation("新增购物卡数据")
    @PostMapping
    @PreAuthorize("@el.check('shopCard:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody ShopCard resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        UserDto user = dataScope.getUser();
        resources.setCreateUserId(user.getId());
        resources.setCreateUserName(user.getUsername());
        resources.setUpdateUserId(user.getId());
        resources.setUpdateUserName(user.getUsername());
        resources.setCompanyId(dataScope.getCompanyId());
        return new ResponseEntity<>(shopCardService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改购物卡数据")
    @ApiOperation("修改购物卡数据")
    @PutMapping
    @PreAuthorize("@el.check('shopCard:edit')")
    public ResponseEntity<Object> update(@Validated(ShopCard.Update.class) @RequestBody ShopCard resources) {
        shopCardService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除下订单数据")
    @ApiOperation("删除订单数据")
    @DeleteMapping
    @PreAuthorize("@el.check('shopCard:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new BadRequestException("删除失败，请选择要删除的订单");
        }
        try {
            shopCardService.delete(ids);
        } catch (Throwable e) {
            ThrowableUtil.throwForeignKeyException(e, "删除失败");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}