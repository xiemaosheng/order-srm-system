package com.order.srm.modules.system.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @date 2019-03-25
 */
@Data
public class MediumInfoDto implements Serializable {

    private Long id;

    private Long companyId;

    private String tag;

    private String weixin;

    private Long addUserId;

    private String remark;

    private Timestamp createTime;
}