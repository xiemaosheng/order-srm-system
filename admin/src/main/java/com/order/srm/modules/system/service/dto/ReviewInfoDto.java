package com.order.srm.modules.system.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @date 2019-03-25
 */
@Data
public class ReviewInfoDto implements Serializable {

    private Long id;

    private Long companyId;

    private Long orderId;

    private Long reviewUserId;

    private String reviewUserName;

    private String reviewAccount;

    private String reviewJtUrl;

    private String reviewLinkUrl;

    private String reviewDateTime;

    private String reviewImgUrl;

    private String reviewVideoUrl;

    private String isFeedbackReview;

    private Timestamp createTime;

    private Timestamp updateTime;

}