package com.order.srm.modules.mnt.service.dto;

import com.order.srm.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author zhanghouying
 * @date 2019-08-24
 */
@Data
public class ServerDeployQueryCriteria {

    /**
     * 模糊
     */
    @Query(blurry = "name,ip,account")
    private String blurry;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
