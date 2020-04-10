package com.order.srm.modules.system.service.impl;

import com.order.srm.modules.system.domain.FeedbackInfo;
import com.order.srm.modules.system.repository.FeedbackInfoRepository;
import com.order.srm.modules.system.service.FeedbackInfoService;
import com.order.srm.modules.system.service.dto.FeedbackInfoDto;
import com.order.srm.modules.system.service.dto.FeedbackInfoQueryCriteria;
import com.order.srm.modules.system.service.mapper.FeedbackInfoMapper;
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
@CacheConfig(cacheNames = "feedbackInfo")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FeedbackInfoServiceImpl implements FeedbackInfoService {

    private final FeedbackInfoRepository feedbackInfoRepository;

    private final FeedbackInfoMapper feedbackInfoMapper;

    public FeedbackInfoServiceImpl(FeedbackInfoRepository feedbackInfoRepository, FeedbackInfoMapper feedbackInfoMapper) {
        this.feedbackInfoRepository = feedbackInfoRepository;
        this.feedbackInfoMapper = feedbackInfoMapper;
    }

    @Override
    public void download(List<FeedbackInfoDto> feedbackInfoDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (FeedbackInfoDto feedbackInfoDto : feedbackInfoDtos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("订单编号", feedbackInfoDto.getOrderId());
            map.put("反馈人", feedbackInfoDto.getFeedbackUserName());
            map.put("是否反馈", feedbackInfoDto.getIsFeedback());
            map.put("是否签收", feedbackInfoDto.getIsSigned());
            map.put("创建日期", feedbackInfoDto.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Cacheable
    public List<FeedbackInfoDto> queryAll(FeedbackInfoQueryCriteria criteria) {
        return feedbackInfoMapper.toDto(feedbackInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public Object queryAll(FeedbackInfoQueryCriteria criteria, Pageable pageable) {
        Page<FeedbackInfo> page = feedbackInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(feedbackInfoMapper::toDto));
    }


    @Override
    @Cacheable(key = "#p0")
    public FeedbackInfoDto findById(Long id) {
        FeedbackInfo feedbackInfo = feedbackInfoRepository.findById(id).orElseGet(FeedbackInfo::new);
        ValidationUtil.isNull(feedbackInfo.getId(), "FeedbackInfo", "id", id);
        return feedbackInfoMapper.toDto(feedbackInfo);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public FeedbackInfoDto create(FeedbackInfo resources) {
        return feedbackInfoMapper.toDto(feedbackInfoRepository.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(FeedbackInfo resources) {
        FeedbackInfo feedbackInfo = feedbackInfoRepository.findById(resources.getId()).orElseGet(FeedbackInfo::new);
        ValidationUtil.isNull(feedbackInfo.getId(), "FeedbackInfo", "id", resources.getId());
        resources.setId(feedbackInfo.getId());
        feedbackInfoRepository.save(resources);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            feedbackInfoRepository.deleteById(id);
        }
    }
}