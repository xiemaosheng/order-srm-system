package com.order.srm.modules.system.service;

import com.order.srm.modules.system.domain.DictDetail;
import com.order.srm.modules.system.service.dto.DictDetailDto;
import com.order.srm.modules.system.service.dto.DictDetailQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 *
 * @date 2019-04-10
 */
public interface DictDetailService {

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    DictDetailDto findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     * @return /
     */
    DictDetailDto create(DictDetail resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(DictDetail resources);

    /**
     * 删除
     *
     * @param id /
     */
    void delete(Long id);

    /**
     * 分页查询
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Map<String, Object> queryAll(DictDetailQueryCriteria criteria, Pageable pageable);
}