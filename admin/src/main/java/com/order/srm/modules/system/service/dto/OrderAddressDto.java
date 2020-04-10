package com.order.srm.modules.system.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @date 2019-03-25
 */
@Data
public class OrderAddressDto implements Serializable {

    private Long id;

    private Long companyId;

    private String website;

    private String addressName;

    private String address;

    private String remark;

    private Boolean enabled;

    private Timestamp createTime;

    public String getLabel() {
        return address;
    }
}