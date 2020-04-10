package com.order.srm.modules.system.service;

import com.order.srm.modules.system.domain.OrderAddress;
import com.order.srm.modules.system.service.dto.OrderAddressDto;
import com.order.srm.modules.system.service.dto.OrderAddressQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @date 2019-03-25
 */
public interface OrderAddressService {
    /**
     * 导出数据
     *
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<OrderAddressDto> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 查询所有数据
     *
     * @param criteria 条件
     * @return /
     */
    List<OrderAddressDto> queryAll(OrderAddressQueryCriteria criteria);

    /**
     * 分页查询
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(OrderAddressQueryCriteria criteria, Pageable pageable);

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    OrderAddressDto findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     * @return /
     */
    OrderAddressDto create(OrderAddress resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(OrderAddress resources);

    /**
     * 删除
     *
     * @param ids /
     */
    void delete(Set<Long> ids);
}