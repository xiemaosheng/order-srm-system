package com.order.srm.modules.system.service.dto;

import com.order.srm.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @date 2019-03-25
 */
@Data
public class MediumInfoQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String weixin;

    @Query
    private Long companyId;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}