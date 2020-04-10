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
public class MakeOrderDto implements Serializable {

    private Long id;

    private Long companyId;

    private Long orderId;

    private Long makeUserId;

    private String makeUserName;

    private Long shopCardId;

    private String makeJtUrl;

    private String makePayedJtUrl;

    private double payPrice;

    private String autoAddress;

    private String selfAddress;

    private String remark;

    private Timestamp createTime;

    private Timestamp updateTime;

}