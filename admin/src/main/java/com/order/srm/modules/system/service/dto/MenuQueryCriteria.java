package com.order.srm.modules.system.service.dto;

import com.order.srm.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * 公共查询类
 */
@Data
public class MenuQueryCriteria {

    @Query(blurry = "name,path,component")
    private String blurry;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
