package com.order.srm.modules.system.service.dto;

import com.order.srm.annotation.Query;
import lombok.Data;

/**
 *
 * @date 2019-04-10
 */
@Data
public class DictDetailQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String label;

    @Query(propName = "name", joinName = "dict")
    private String dictName;
}