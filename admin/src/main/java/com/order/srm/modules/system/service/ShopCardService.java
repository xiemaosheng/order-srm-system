package com.order.srm.modules.system.service;

import com.order.srm.modules.system.domain.ShopCard;
import com.order.srm.modules.system.service.dto.ShopCardDto;
import com.order.srm.modules.system.service.dto.ShopCardQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @date 2019-03-25
 */
public interface ShopCardService {
    /**
     * 导出数据
     *
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<ShopCardDto> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 查询所有数据
     *
     * @param criteria 条件
     * @return /
     */
    List<ShopCardDto> queryAll(ShopCardQueryCriteria criteria);

    /**
     * 分页查询
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(ShopCardQueryCriteria criteria, Pageable pageable);

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    ShopCardDto findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     * @return /
     */
    ShopCardDto create(ShopCard resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(ShopCard resources);

    /**
     * 删除
     *
     * @param ids /
     */
    void delete(Set<Long> ids);
}