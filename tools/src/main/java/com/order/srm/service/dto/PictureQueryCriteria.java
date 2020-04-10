package com.order.srm.service.dto;

import com.order.srm.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * sm.ms图床
 *
 *
 * @date 2019-6-4 09:52:09
 */
@Data
public class PictureQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String filename;

    @Query(type = Query.Type.INNER_LIKE)
    private String username;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
