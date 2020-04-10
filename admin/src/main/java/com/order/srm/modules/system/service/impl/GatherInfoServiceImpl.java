package com.order.srm.modules.system.service.impl;

import com.order.srm.modules.system.domain.GatherInfo;
import com.order.srm.modules.system.repository.GatherInfoRepository;
import com.order.srm.modules.system.service.GatherInfoService;
import com.order.srm.modules.system.service.dto.GatherInfoDto;
import com.order.srm.modules.system.service.dto.GatherInfoQueryCriteria;
import com.order.srm.modules.system.service.mapper.GatherInfoMapper;
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
@CacheConfig(cacheNames = "gatherInfo")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class GatherInfoServiceImpl implements GatherInfoService {

    private final GatherInfoRepository gatherInfoRepository;

    private final GatherInfoMapper gatherInfoMapper;

    public GatherInfoServiceImpl(GatherInfoRepository gatherInfoRepository, GatherInfoMapper gatherInfoMapper) {
        this.gatherInfoRepository = gatherInfoRepository;
        this.gatherInfoMapper = gatherInfoMapper;
    }

    @Override
    public void download(List<GatherInfoDto> GatherInfoDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (GatherInfoDto gatherInfoDto : GatherInfoDtos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("订单编号", gatherInfoDto.getOrderId());
            map.put("收款人", gatherInfoDto.getGatherUserName());
            map.put("收款金额", gatherInfoDto.getGatherPrice());
            map.put("收款佣金", gatherInfoDto.getGatherCommission());
            map.put("收款时间", gatherInfoDto.getGatherDateTime());
            map.put("是否可以收款", gatherInfoDto.getIsCanGather());
            map.put("是否收款", gatherInfoDto.getIsGather());
            map.put("创建日期", gatherInfoDto.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Cacheable
    public List<GatherInfoDto> queryAll(GatherInfoQueryCriteria criteria) {
        return gatherInfoMapper.toDto(gatherInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public Object queryAll(GatherInfoQueryCriteria criteria, Pageable pageable) {
        Page<GatherInfo> page = gatherInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(gatherInfoMapper::toDto));
    }


    @Override
    @Cacheable(key = "#p0")
    public GatherInfoDto findById(Long id) {
        GatherInfo gatherInfo = gatherInfoRepository.findById(id).orElseGet(GatherInfo::new);
        ValidationUtil.isNull(gatherInfo.getId(), "GatherInfo", "id", id);
        return gatherInfoMapper.toDto(gatherInfo);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public GatherInfoDto create(GatherInfo resources) {
        return gatherInfoMapper.toDto(gatherInfoRepository.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(GatherInfo resources) {
        GatherInfo gatherInfo = gatherInfoRepository.findById(resources.getId()).orElseGet(GatherInfo::new);
        ValidationUtil.isNull(gatherInfo.getId(), "GatherInfo", "id", resources.getId());
        resources.setId(gatherInfo.getId());
        gatherInfoRepository.save(resources);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            gatherInfoRepository.deleteById(id);
        }
    }
}