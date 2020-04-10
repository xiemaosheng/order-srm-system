package com.order.srm.modules.system.service.impl;

import com.order.srm.modules.system.domain.Company;
import com.order.srm.modules.system.repository.CompanyRepository;
import com.order.srm.modules.system.service.CompanyService;
import com.order.srm.modules.system.service.dto.CompanyDto;
import com.order.srm.modules.system.service.dto.CompanyQueryCriteria;
import com.order.srm.modules.system.service.mapper.CompanyMapper;
import com.order.srm.utils.FileUtil;
import com.order.srm.utils.QueryHelp;
import com.order.srm.utils.ValidationUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
@CacheConfig(cacheNames = "company")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    @Override
    public void download(List<CompanyDto> companyDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CompanyDto companyDto : companyDtos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("公司名称", companyDto.getName());
            map.put("公司编码", companyDto.getCode());
            map.put("公司状态", companyDto.getEnabled() ? "启用" : "停用");
            map.put("创建日期", companyDto.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Cacheable
    public List<CompanyDto> queryAll(CompanyQueryCriteria criteria) {
        return companyMapper.toDto(companyRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }


    @Override
    @Cacheable(key = "#p0")
    public CompanyDto findById(Long id) {
        Company company = companyRepository.findById(id).orElseGet(Company::new);
        ValidationUtil.isNull(company.getId(), "Company", "id", id);
        return companyMapper.toDto(company);
    }

    @Override
    @Cacheable(key = "#p0")
    public CompanyDto findByCode(String code) {
        Company company = companyRepository.findByCode(code).orElseGet(Company::new);
        ValidationUtil.isNull(company.getCode(), "Company", "code", code);
        return companyMapper.toDto(company);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public CompanyDto create(Company resources) {
        return companyMapper.toDto(companyRepository.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Company resources) {
        Company company = companyRepository.findById(resources.getId()).orElseGet(Company::new);
        ValidationUtil.isNull(company.getId(), "Company", "id", resources.getId());
        resources.setId(company.getId());
        companyRepository.save(resources);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            companyRepository.deleteById(id);
        }
    }
}