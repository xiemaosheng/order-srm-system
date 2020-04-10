package com.order.srm.modules.system.service.dto;

import com.order.srm.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @date 2019-03-25
 */
@Data
public class ReviewInfoQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String mktName;

    @Query(type = Query.Type.INNER_LIKE)
    private String reviewUserName;

    @Query
    private String isFeedbackReview;

    @Query
    private Long companyId;

    @Query
    private Long orderId;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}