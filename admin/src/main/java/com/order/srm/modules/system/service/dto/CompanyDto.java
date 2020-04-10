package com.order.srm.modules.system.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @date 2019-03-25
 */
@Data
public class CompanyDto implements Serializable {

    private Long id;

    private String code;

    private String name;

    private Boolean enabled;

    private Timestamp createTime;

    public String getLabel() {
        return name;
    }
}