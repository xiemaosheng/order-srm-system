package com.order.srm.service.dto;

import com.order.srm.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @date 2019-09-05
 */
@Data
public class LocalStorageQueryCriteria {

    @Query(blurry = "name,suffix,type,operate,size")
    private String blurry;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}