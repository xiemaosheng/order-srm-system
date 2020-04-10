package com.order.srm.modules.system.service;

import com.order.srm.modules.system.domain.Order;
import com.order.srm.modules.system.service.dto.OrderDto;
import com.order.srm.modules.system.service.dto.OrderQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @date 2019-03-25
 */
public interface OrderService {
    /**
     * 导出数据
     *
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<OrderDto> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 查询所有数据
     *
     * @param criteria 条件
     * @return /
     */
    List<OrderDto> queryAll(OrderQueryCriteria criteria);

    /**
     * 分页查询
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(OrderQueryCriteria criteria, Pageable pageable);

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    OrderDto findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     * @return /
     */
    OrderDto create(Order resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(Order resources);

    /**
     * 删除
     *
     * @param ids /
     */
    void delete(Set<Long> ids);
}