package com.order.srm.modules.system.service.dto;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @date 2019-03-25
 */
@Data
public class GatherInfoDto implements Serializable {

    private Long id;

    private Long companyId;

    private Long orderId;

    private Long gatherUserId;

    private String gatherUserName;

    private String gatherJtUrl;

    private String gatherDateTime;

    private double gatherPrice;

    private String gatherCommission;

    private String isCanGather;

    private String isGather;

    private Timestamp createTime;

    private Timestamp updateTime;

}