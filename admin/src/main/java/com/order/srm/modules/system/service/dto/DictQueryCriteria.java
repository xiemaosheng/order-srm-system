package com.order.srm.modules.system.service.dto;

import com.order.srm.annotation.Query;
import lombok.Data;

/**
 *
 * 公共查询类
 */
@Data
public class DictQueryCriteria {

    @Query(blurry = "name,remark")
    private String blurry;
}
