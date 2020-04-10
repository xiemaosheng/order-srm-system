package com.order.srm.modules.system.service;

import com.order.srm.modules.system.domain.ReviewInfo;
import com.order.srm.modules.system.service.dto.ReviewInfoDto;
import com.order.srm.modules.system.service.dto.ReviewInfoQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @date 2019-03-25
 */
public interface ReviewInfoService {
    /**
     * 导出数据
     *
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<ReviewInfoDto> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 查询所有数据
     *
     * @param criteria 条件
     * @return /
     */
    List<ReviewInfoDto> queryAll(ReviewInfoQueryCriteria criteria);

    /**
     * 分页查询
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(ReviewInfoQueryCriteria criteria, Pageable pageable);

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    ReviewInfoDto findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     * @return /
     */
    ReviewInfoDto create(ReviewInfo resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(ReviewInfo resources);

    /**
     * 删除
     *
     * @param ids /
     */
    void delete(Set<Long> ids);
}