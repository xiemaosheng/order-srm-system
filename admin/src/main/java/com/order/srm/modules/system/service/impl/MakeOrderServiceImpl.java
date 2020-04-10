package com.order.srm.modules.system.service.impl;

import com.order.srm.modules.system.domain.MakeOrder;
import com.order.srm.modules.system.repository.MakeOrderRepository;
import com.order.srm.modules.system.service.MakeOrderService;
import com.order.srm.modules.system.service.dto.MakeOrderDto;
import com.order.srm.modules.system.service.dto.MakeOrderQueryCriteria;
import com.order.srm.modules.system.service.mapper.MakeOrderMapper;
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
@CacheConfig(cacheNames = "makeOrder")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MakeOrderServiceImpl implements MakeOrderService {

    private final MakeOrderRepository makeOrderRepository;

    private final MakeOrderMapper makeOrderMapper;

    public MakeOrderServiceImpl(MakeOrderRepository makeOrderRepository, MakeOrderMapper makeOrderMapper) {
        this.makeOrderRepository = makeOrderRepository;
        this.makeOrderMapper = makeOrderMapper;
    }

    @Override
    public void download(List<MakeOrderDto> makeOrderDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MakeOrderDto orderDto : makeOrderDtos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("订单编号", orderDto.getOrderId());
            map.put("下单人", orderDto.getMakeUserName());
            map.put("支付金额", orderDto.getPayPrice());
            map.put("备注", orderDto.getRemark());
            map.put("创建日期", orderDto.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Cacheable
    public List<MakeOrderDto> queryAll(MakeOrderQueryCriteria criteria) {
        return makeOrderMapper.toDto(makeOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public Object queryAll(MakeOrderQueryCriteria criteria, Pageable pageable) {
        Page<MakeOrder> page = makeOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(makeOrderMapper::toDto));
    }


    @Override
    @Cacheable(key = "#p0")
    public MakeOrderDto findById(Long id) {
        MakeOrder order = makeOrderRepository.findById(id).orElseGet(MakeOrder::new);
        ValidationUtil.isNull(order.getId(), "MakeOrder", "id", id);
        return makeOrderMapper.toDto(order);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public MakeOrderDto create(MakeOrder resources) {
        return makeOrderMapper.toDto(makeOrderRepository.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(MakeOrder resources) {
        MakeOrder order = makeOrderRepository.findById(resources.getId()).orElseGet(MakeOrder::new);
        ValidationUtil.isNull(order.getId(), "MakeOrder", "id", resources.getId());
        resources.setId(order.getId());
        makeOrderRepository.save(resources);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            makeOrderRepository.deleteById(id);
        }
    }
}