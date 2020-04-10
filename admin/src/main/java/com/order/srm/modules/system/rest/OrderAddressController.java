package com.order.srm.modules.system.rest;

import com.order.srm.config.DataScope;
import com.order.srm.exception.BadRequestException;
import com.order.srm.log.Log;
import com.order.srm.modules.mnt.service.dto.ServerDeployQueryCriteria;
import com.order.srm.modules.system.domain.Company;
import com.order.srm.modules.system.domain.Dept;
import com.order.srm.modules.system.domain.OrderAddress;
import com.order.srm.modules.system.service.CompanyService;
import com.order.srm.modules.system.service.OrderAddressService;
import com.order.srm.modules.system.service.dto.CompanyDto;
import com.order.srm.modules.system.service.dto.CompanyQueryCriteria;
import com.order.srm.modules.system.service.dto.OrderAddressQueryCriteria;
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
import java.util.*;

/**
 * @date 2019-03-25
 */
@RestController
@Api(tags = "资料库：下单地址管理")
@RequestMapping("/api/orderAddress")
public class OrderAddressController {

    private final OrderAddressService orderAddressService;

    private final DataScope dataScope;

    private static final String ENTITY_NAME = "orderAddress";

    public OrderAddressController(OrderAddressService orderAddressService, DataScope dataScope) {
        this.orderAddressService = orderAddressService;
        this.dataScope = dataScope;
    }

    @Log("导出下单地址数据")
    @ApiOperation("导出下单地址数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('orderAddress:list')")
    public void download(HttpServletResponse response, OrderAddressQueryCriteria criteria) throws IOException {
        orderAddressService.download(orderAddressService.queryAll(criteria), response);
    }

    @Log("查询下单地址")
    @ApiOperation("查询下单地址")
    @GetMapping
    @PreAuthorize("@el.check('user:list','orderAddress:list')")
    public ResponseEntity<Object> getOrderAddress(OrderAddressQueryCriteria criteria, Pageable pageable) {
        // 数据权限
        criteria.setCompanyId(dataScope.getCompanyId());
        return new ResponseEntity<>(orderAddressService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("新增下单地址")
    @ApiOperation("新增下单地址")
    @PostMapping
    @PreAuthorize("@el.check('orderAddress:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody OrderAddress resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        resources.setCompanyId(dataScope.getCompanyId());
        return new ResponseEntity<>(orderAddressService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改下单地址")
    @ApiOperation("修改下单地址")
    @PutMapping
    @PreAuthorize("@el.check('orderAddress:edit')")
    public ResponseEntity<Object> update(@Validated(OrderAddress.Update.class) @RequestBody OrderAddress resources) {
        orderAddressService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除下单地址")
    @ApiOperation("删除下单地址")
    @DeleteMapping
    @PreAuthorize("@el.check('orderAddress:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new BadRequestException("删除失败，请选择要删除的地址");
        }
        try {
            orderAddressService.delete(ids);
        } catch (Throwable e) {
            ThrowableUtil.throwForeignKeyException(e, "删除失败");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}