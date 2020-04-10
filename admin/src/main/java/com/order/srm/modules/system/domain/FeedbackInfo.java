package com.order.srm.modules.system.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "srm_feedback_info")
public class FeedbackInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = FeedbackInfo.Update.class)
    private Long id;

    /**
     * 公司id
     */
    @Column(name = "company_id", nullable = false)
    private Long companyId;

    /**
     * 订单id
     */
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    /**
     * 反馈人id
     */
    @Column(name = "feedback_user_id", nullable = false)
    private Long feedbackUserId;

    /**
     * 反馈人名称
     */
    @Column(name = "feedback_user_name", nullable = false)
    private String feedbackUserName;


    /**
     * 签收截图url
     */
    @Column(name = "signed_jt_url")
    private String signedJtUrl;

    /**
     * 是否反馈下单
     */
    @Column(name = "is_feedback")
    private String isFeedback;

    /**
     * 是否签收
     */
    @Column(name = "is_signed")
    private String isSigned;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @CreationTimestamp
    private Timestamp updateTime;

    public @interface Update {
    }
}
