package com.order.srm.modules.system.service.impl;

import com.order.srm.modules.system.domain.Order;
import com.order.srm.modules.system.repository.OrderRepository;
import com.order.srm.modules.system.service.OrderService;
import com.order.srm.modules.system.service.dto.OrderDto;
import com.order.srm.modules.system.service.dto.OrderQueryCriteria;
import com.order.srm.modules.system.service.mapper.OrderMapper;
import com.order.srm.utils.FileUtil;
import com.order.srm.utils.PageUtil;
import com.order.srm.utils.QueryHelp;
import com.order.srm.utils.ValidationUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @date 2019-03-25
 */
@Service
@CacheConfig(cacheNames = "order")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderAddressRepository, OrderMapper orderMapper) {
        this.orderRepository = orderAddressRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public void download(List<OrderDto> orderDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (OrderDto orderDto : orderDtos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("店铺名称", orderDto.getMktName());
            map.put("站点", orderDto.getWebsite());
            map.put("接单人", orderDto.getRecUserName());
            map.put("工作性质", orderDto.getWorkerType());
            map.put("卖家联系方式", orderDto.getSellerContactType());
            map.put("卖家联系方式", orderDto.getSellerContactType());
            map.put("卖家订单类型", orderDto.getSellerOrderType());
            map.put("ASIN", orderDto.getASIN());
            map.put("返款方式", orderDto.getCashBackType());
            map.put("接单金额", orderDto.getRecPrice());
            map.put("接单佣金", orderDto.getRecCommission());
            map.put("下单要求", orderDto.getXdRequirement());
            map.put("备注", orderDto.getRemark());
            map.put("接单日期", orderDto.getRecDateTime());
            map.put("计划上评日期", orderDto.getPlanReviewDateTime());
            map.put("追踪号", orderDto.getTrackNo());
            map.put("是否留评", orderDto.getIsComment());
            map.put("是否到货拍照", orderDto.getIsPhoto());
            map.put("是否换仓", orderDto.getIsBinShift());
            map.put("是否回收", orderDto.getIsRecover());
            map.put("回收佣金", orderDto.getRecoverCommission());
            map.put("状态", orderDto.getStatus());
            map.put("创建日期", orderDto.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Cacheable
    public List<OrderDto> queryAll(OrderQueryCriteria criteria) {
        return orderMapper.toDto(orderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public Object queryAll(OrderQueryCriteria criteria, Pageable pageable) {
        Page<Order> page = orderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(orderMapper::toDto));
    }


    @Override
    @Cacheable(key = "#p0")
    public OrderDto findById(Long id) {
        Order order = orderRepository.findById(id).orElseGet(Order::new);
        ValidationUtil.isNull(order.getId(), "Order", "id", id);
        return orderMapper.toDto(order);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public OrderDto create(Order resources) {
        return orderMapper.toDto(orderRepository.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Order resources) {
        Order order = orderRepository.findById(resources.getId()).orElseGet(Order::new);
        ValidationUtil.isNull(order.getId(), "Order", "id", resources.getId());
        resources.setId(order.getId());
        orderRepository.save(resources);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            orderRepository.deleteById(id);
        }
    }
}