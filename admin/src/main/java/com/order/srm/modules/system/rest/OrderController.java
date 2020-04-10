package com.order.srm.modules.system.rest;

import com.order.srm.config.DataScope;
import com.order.srm.exception.BadRequestException;
import com.order.srm.log.Log;
import com.order.srm.modules.system.domain.MakeOrder;
import com.order.srm.modules.system.domain.Order;
import com.order.srm.modules.system.service.OrderService;
import com.order.srm.modules.system.service.dto.OrderQueryCriteria;
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
@Api(tags = "订单管理")
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    private final DataScope dataScope;

    private static final String ENTITY_NAME = "order";

    public OrderController(OrderService orderService, DataScope dataScope) {
        this.orderService = orderService;
        this.dataScope = dataScope;
    }

    @Log("导出订单数据")
    @ApiOperation("导出订单数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('order:list')")
    public void download(HttpServletResponse response, OrderQueryCriteria criteria) throws IOException {
        orderService.download(orderService.queryAll(criteria), response);
    }

    @Log("查询订单数据")
    @ApiOperation("查询订单数据")
    @GetMapping
    @PreAuthorize("@el.check('user:list','order:list')")
    public ResponseEntity<Object> getOrderAddress(OrderQueryCriteria criteria, Pageable pageable) {
        // 数据权限
        criteria.setCompanyId(dataScope.getCompanyId());
        return new ResponseEntity<>(orderService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("新增订单数据")
    @ApiOperation("新增订单数据")
    @PostMapping
    @PreAuthorize("@el.check('order:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Order resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        UserDto user = dataScope.getUser();
        resources.setRecUserId(user.getId());
        resources.setRecUserName(user.getUsername());
        resources.setCompanyId(dataScope.getCompanyId());
        return new ResponseEntity<>(orderService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改下订单数据")
    @ApiOperation("修改订单数据")
    @PutMapping
    @PreAuthorize("@el.check('order:edit')")
    public ResponseEntity<Object> update(@Validated(Order.Update.class) @RequestBody Order resources) {
        orderService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除下订单数据")
    @ApiOperation("删除订单数据")
    @DeleteMapping
    @PreAuthorize("@el.check('order:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new BadRequestException("删除失败，请选择要删除的订单");
        }
        try {
            orderService.delete(ids);
        } catch (Throwable e) {
            ThrowableUtil.throwForeignKeyException(e, "删除失败");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Log("新增下单数据")
    @ApiOperation("新增下单数据")
    @PostMapping(value = "/makeOrder")
    @PreAuthorize("@el.check('order:add')")
    public ResponseEntity<Object> makeOrder(@Validated @RequestBody MakeOrder resources) {
        UserDto user = dataScope.getUser();
//        resources.setRecUserId(user.getId());
//        resources.setRecUserName(user.getUsername());
//        resources.setCompanyId(dataScope.getCompanyId());
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }
}