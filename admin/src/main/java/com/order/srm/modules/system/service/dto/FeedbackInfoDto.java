package com.order.srm.modules.system.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @date 2019-03-25
 */
@Data
public class FeedbackInfoDto implements Serializable {

    private Long id;

    private Long companyId;

    private Long orderId;

    private Long feedbackUserId;

    private String feedbackUserName;

    private String signedJtUrl;

    private String isFeedback;

    private String isSigned;

    private Timestamp createTime;

    private Timestamp updateTime;

}