package com.order.srm.modules.system.service.impl;

import com.order.srm.modules.system.domain.ReviewInfo;
import com.order.srm.modules.system.repository.ReviewInfoRepository;
import com.order.srm.modules.system.service.ReviewInfoService;
import com.order.srm.modules.system.service.dto.ReviewInfoDto;
import com.order.srm.modules.system.service.dto.ReviewInfoQueryCriteria;
import com.order.srm.modules.system.service.mapper.ReviewInfoMapper;
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
@CacheConfig(cacheNames = "reviewInfo")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ReviewInfoServiceImpl implements ReviewInfoService {

    private final ReviewInfoRepository reviewInfoRepository;

    private final ReviewInfoMapper reviewInfoMapper;

    public ReviewInfoServiceImpl(ReviewInfoRepository reviewInfoRepository, ReviewInfoMapper reviewInfoMapper) {
        this.reviewInfoRepository = reviewInfoRepository;
        this.reviewInfoMapper = reviewInfoMapper;
    }

    @Override
    public void download(List<ReviewInfoDto> reviewInfoDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ReviewInfoDto reviewInfoDto : reviewInfoDtos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("订单编号", reviewInfoDto.getOrderId());
            map.put("上评人", reviewInfoDto.getReviewUserName());
            map.put("上评账号", reviewInfoDto.getReviewAccount());
            map.put("上评时间", reviewInfoDto.getReviewDateTime());
            map.put("是否反馈上评", reviewInfoDto.getIsFeedbackReview());
            map.put("创建日期", reviewInfoDto.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Cacheable
    public List<ReviewInfoDto> queryAll(ReviewInfoQueryCriteria criteria) {
        return reviewInfoMapper.toDto(reviewInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public Object queryAll(ReviewInfoQueryCriteria criteria, Pageable pageable) {
        Page<ReviewInfo> page = reviewInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(reviewInfoMapper::toDto));
    }


    @Override
    @Cacheable(key = "#p0")
    public ReviewInfoDto findById(Long id) {
        ReviewInfo reviewInfo = reviewInfoRepository.findById(id).orElseGet(ReviewInfo::new);
        ValidationUtil.isNull(reviewInfo.getId(), "ReviewInfo", "id", id);
        return reviewInfoMapper.toDto(reviewInfo);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public ReviewInfoDto create(ReviewInfo resources) {
        return reviewInfoMapper.toDto(reviewInfoRepository.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(ReviewInfo resources) {
        ReviewInfo reviewInfo = reviewInfoRepository.findById(resources.getId()).orElseGet(ReviewInfo::new);
        ValidationUtil.isNull(reviewInfo.getId(), "ReviewInfo", "id", resources.getId());
        resources.setId(reviewInfo.getId());
        reviewInfoRepository.save(resources);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            reviewInfoRepository.deleteById(id);
        }
    }
}