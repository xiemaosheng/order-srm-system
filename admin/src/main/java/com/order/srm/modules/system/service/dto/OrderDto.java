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
public class OrderDto implements Serializable {

    private Long id;

    private Long companyId;

    private String mktName;

    private String goodImgUrl;

    private String goodOrderNum;

    private Long recUserId;

    private String recUserName;

    private int orderCount;

    private int dayCount;

    private String workerType;

    private String sellerContactType;

    private String sellerOrderType;

    private int cashBackType;

    private String website;

    private String ASIN;

    private String xdRequirement;

    private String remark;

    private String trackNo;

    private String title;

    private String content;

    private String recDateTime;

    private String planReviewDateTime;

    private double recPrice;

    private double recCommission;

    private double recoverCommission;

    private Long status;

    private String statusText;

    private String isComment;

    private String isPhoto;

    private String isBinShift;

    private String isRecover;

    private Timestamp createTime;

    private Timestamp updateTime;

}