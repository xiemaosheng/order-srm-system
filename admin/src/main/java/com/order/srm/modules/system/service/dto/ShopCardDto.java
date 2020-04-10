package com.order.srm.modules.system.service.dto;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @date 2018-11-23
 */
@Data
public class ShopCardDto implements Serializable {

    private Long id;

    private Long companyId;

    private String shopCardId;

    private String shopCardAccount;

    private String shopCardPassword;

    private String shopCardUserName;

    private String website;

    private String shopCardType;

    private Integer status;

    private String isCanComment;

    private Double initAmount;

    private Double totalAmount;

    private Double accountAmount;

    private Date upgradeTime;

    private Date endTime;

    private String phone;

    private String helpEmail;

    private String studentEmail;

    private String studentEmailPwd;

    private String remark;

    private Long createUserId;

    private String createUserName;

    private Long updateUserId;

    private String updateUserName;

    @CreationTimestamp
    private Timestamp createTime;

    @CreationTimestamp
    private Timestamp updateTime;
}
