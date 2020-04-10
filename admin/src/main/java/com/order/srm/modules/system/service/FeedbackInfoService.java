package com.order.srm.modules.system.service;

import com.order.srm.modules.system.domain.FeedbackInfo;
import com.order.srm.modules.system.service.dto.FeedbackInfoDto;
import com.order.srm.modules.system.service.dto.FeedbackInfoQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @date 2019-03-25
 */
public interface FeedbackInfoService {
    /**
     * 导出数据
     *
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<FeedbackInfoDto> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 查询所有数据
     *
     * @param criteria 条件
     * @return /
     */
    List<FeedbackInfoDto> queryAll(FeedbackInfoQueryCriteria criteria);

    /**
     * 分页查询
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(FeedbackInfoQueryCriteria criteria, Pageable pageable);

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    FeedbackInfoDto findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     * @return /
     */
    FeedbackInfoDto create(FeedbackInfo resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(FeedbackInfo resources);

    /**
     * 删除
     *
     * @param ids /
     */
    void delete(Set<Long> ids);
}