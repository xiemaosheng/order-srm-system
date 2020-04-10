package com.order.srm.modules.system.service.impl;

import com.order.srm.modules.mnt.domain.ServerDeploy;
import com.order.srm.modules.mnt.service.dto.ServerDeployQueryCriteria;
import com.order.srm.modules.system.domain.Company;
import com.order.srm.modules.system.domain.OrderAddress;
import com.order.srm.modules.system.repository.CompanyRepository;
import com.order.srm.modules.system.repository.OrderAddressRepository;
import com.order.srm.modules.system.service.OrderAddressService;
import com.order.srm.modules.system.service.dto.CompanyDto;
import com.order.srm.modules.system.service.dto.CompanyQueryCriteria;
import com.order.srm.modules.system.service.dto.OrderAddressDto;
import com.order.srm.modules.system.service.dto.OrderAddressQueryCriteria;
import com.order.srm.modules.system.service.mapper.CompanyMapper;
import com.order.srm.modules.system.service.mapper.OrderAddressMapper;
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
@CacheConfig(cacheNames = "orderAddress")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class OrderAddressServiceImpl implements OrderAddressService {

    private final OrderAddressRepository orderAddressRepository;

    private final OrderAddressMapper orderAddressMapper;

    public OrderAddressServiceImpl(OrderAddressRepository orderAddressRepository, OrderAddressMapper orderAddressMapper) {
        this.orderAddressRepository = orderAddressRepository;
        this.orderAddressMapper = orderAddressMapper;
    }

    @Override
    public void download(List<OrderAddressDto> orderAddressDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (OrderAddressDto orderAddressDto : orderAddressDtos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("地址名称", orderAddressDto.getAddressName());
            map.put("地址", orderAddressDto.getAddress());
            map.put("站点", orderAddressDto.getWebsite());
            map.put("状态", orderAddressDto.getEnabled() ? "启用" : "停用");
            map.put("创建日期", orderAddressDto.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Cacheable
    public List<OrderAddressDto> queryAll(OrderAddressQueryCriteria criteria) {
        return orderAddressMapper.toDto(orderAddressRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public Object queryAll(OrderAddressQueryCriteria criteria, Pageable pageable) {
        Page<OrderAddress> page = orderAddressRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(orderAddressMapper::toDto));
    }


    @Override
    @Cacheable(key = "#p0")
    public OrderAddressDto findById(Long id) {
        OrderAddress orderAddress = orderAddressRepository.findById(id).orElseGet(OrderAddress::new);
        ValidationUtil.isNull(orderAddress.getId(), "OrderAddress", "id", id);
        return orderAddressMapper.toDto(orderAddress);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public OrderAddressDto create(OrderAddress resources) {
        return orderAddressMapper.toDto(orderAddressRepository.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(OrderAddress resources) {
        OrderAddress orderAddress = orderAddressRepository.findById(resources.getId()).orElseGet(OrderAddress::new);
        ValidationUtil.isNull(orderAddress.getId(), "OrderAddress", "id", resources.getId());
        resources.setId(orderAddress.getId());
        orderAddressRepository.save(resources);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            orderAddressRepository.deleteById(id);
        }
    }
}