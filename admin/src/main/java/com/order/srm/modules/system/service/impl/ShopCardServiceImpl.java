package com.order.srm.modules.system.service.impl;

import com.order.srm.modules.system.domain.ShopCard;
import com.order.srm.modules.system.repository.ShopCardRepository;
import com.order.srm.modules.system.service.ShopCardService;
import com.order.srm.modules.system.service.dto.ShopCardDto;
import com.order.srm.modules.system.service.dto.ShopCardQueryCriteria;
import com.order.srm.modules.system.service.mapper.ShopCardMapper;
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
@CacheConfig(cacheNames = "shopCard")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ShopCardServiceImpl implements ShopCardService {

    private final ShopCardRepository shopCardRepository;

    private final ShopCardMapper shopCardMapper;

    public ShopCardServiceImpl(ShopCardRepository shopCardRepository, ShopCardMapper shopCardMapper) {
        this.shopCardRepository = shopCardRepository;
        this.shopCardMapper = shopCardMapper;
    }

    @Override
    public void download(List<ShopCardDto> shopCardDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ShopCardDto shopCardDto : shopCardDtos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("站点", shopCardDto.getWebsite());
            map.put("创建人", shopCardDto.getCreateUserName());
            map.put("更新人", shopCardDto.getUpdateUserName());
            map.put("购物卡id", shopCardDto.getShopCardId());
            map.put("购物卡账号", shopCardDto.getShopCardAccount());
            map.put("姓名", shopCardDto.getShopCardUserName());
            map.put("密码", shopCardDto.getShopCardPassword());

            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Cacheable
    public List<ShopCardDto> queryAll(ShopCardQueryCriteria criteria) {
        return shopCardMapper.toDto(shopCardRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public Object queryAll(ShopCardQueryCriteria criteria, Pageable pageable) {
        Page<ShopCard> page = shopCardRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(shopCardMapper::toDto));
    }


    @Override
    @Cacheable(key = "#p0")
    public ShopCardDto findById(Long id) {
        ShopCard shopCard = shopCardRepository.findById(id).orElseGet(ShopCard::new);
        ValidationUtil.isNull(shopCard.getId(), "ShopCard", "id", id);
        return shopCardMapper.toDto(shopCard);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public ShopCardDto create(ShopCard resources) {
        return shopCardMapper.toDto(shopCardRepository.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(ShopCard resources) {
        ShopCard shopCard = shopCardRepository.findById(resources.getId()).orElseGet(ShopCard::new);
        ValidationUtil.isNull(shopCard.getId(), "ShopCard", "id", resources.getId());
        resources.setId(shopCard.getId());
        shopCardRepository.save(resources);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            shopCardRepository.deleteById(id);
        }
    }
}