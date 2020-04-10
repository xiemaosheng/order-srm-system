package com.order.srm.modules.system.service.impl;

import com.order.srm.modules.system.domain.MediumInfo;
import com.order.srm.modules.system.repository.MediumInfoRepository;
import com.order.srm.modules.system.service.MediumInfoService;
import com.order.srm.modules.system.service.dto.MediumInfoDto;
import com.order.srm.modules.system.service.dto.MediumInfoQueryCriteria;
import com.order.srm.modules.system.service.mapper.MediumInfoMapper;
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
@CacheConfig(cacheNames = "mediumInfo")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MediumInfoServiceImpl implements MediumInfoService {

    private final MediumInfoRepository mediumInfoRepository;

    private final MediumInfoMapper mediumInfoMapper;

    public MediumInfoServiceImpl(MediumInfoRepository mediumInfoRepository, MediumInfoMapper mediumInfoMapper) {
        this.mediumInfoRepository = mediumInfoRepository;
        this.mediumInfoMapper = mediumInfoMapper;
    }

    @Override
    public void download(List<MediumInfoDto> mediumInfoDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MediumInfoDto mediumInfoDto : mediumInfoDtos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("微信号", mediumInfoDto.getWeixin());
            map.put("标签", mediumInfoDto.getTag());
            map.put("创建日期", mediumInfoDto.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Cacheable
    public List<MediumInfoDto> queryAll(MediumInfoQueryCriteria criteria) {
        return mediumInfoMapper.toDto(mediumInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public Object queryAll(MediumInfoQueryCriteria criteria, Pageable pageable) {
        Page<MediumInfo> page = mediumInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(mediumInfoMapper::toDto));
    }


    @Override
    @Cacheable(key = "#p0")
    public MediumInfoDto findById(Long id) {
        MediumInfo mediumInfo = mediumInfoRepository.findById(id).orElseGet(MediumInfo::new);
        ValidationUtil.isNull(mediumInfo.getId(), "MediumInfo", "id", id);
        return mediumInfoMapper.toDto(mediumInfo);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public MediumInfoDto create(MediumInfo resources) {
        return mediumInfoMapper.toDto(mediumInfoRepository.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(MediumInfo resources) {
        MediumInfo MediunInfo = mediumInfoRepository.findById(resources.getId()).orElseGet(MediumInfo::new);
        ValidationUtil.isNull(MediunInfo.getId(), "MediunInfo", "id", resources.getId());
        resources.setId(MediunInfo.getId());
        mediumInfoRepository.save(resources);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            mediumInfoRepository.deleteById(id);
        }
    }
}