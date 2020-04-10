package com.order.srm.modules.system.service;

import com.order.srm.modules.system.domain.Company;
import com.order.srm.modules.system.service.dto.CompanyDto;
import com.order.srm.modules.system.service.dto.CompanyQueryCriteria;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @date 2019-03-25
 */
public interface CompanyService {
    /**
     * 导出数据
     *
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<CompanyDto> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 查询所有数据
     *
     * @param criteria 条件
     * @return /
     */
    List<CompanyDto> queryAll(CompanyQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    CompanyDto findById(Long id);

    /**
     * @param code
     * @return
     */
    CompanyDto findByCode(String code);

    /**
     * 创建
     *
     * @param resources /
     * @return /
     */
    CompanyDto create(Company resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(Company resources);

    /**
     * 删除
     *
     * @param ids /
     */
    void delete(Set<Long> ids);
}