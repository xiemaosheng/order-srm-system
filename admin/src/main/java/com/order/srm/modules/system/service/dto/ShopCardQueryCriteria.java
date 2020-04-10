package com.order.srm.modules.system.service.dto;

import com.order.srm.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @date 2019-03-25
 */
@Data
public class ShopCardQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String updateUserName;

    @Query(type = Query.Type.INNER_LIKE)
    private String createUserName;

    @Query
    private String website;

    @Query
    private Long companyId;

    @Query(type = Query.Type.IN)
    private List<Long> status;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}