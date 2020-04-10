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
@Table(name = "srm_review_info")
public class ReviewInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = ReviewInfo.Update.class)
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
     * 上评人id
     */
    @Column(name = "review_user_id", nullable = false)
    private Long reviewUserId;

    /**
     * 上评人名称
     */
    @Column(name = "review_user_name", nullable = false)
    private String reviewUserName;

    /**
     * 上评账号
     */
    @Column(name = "review_account", nullable = false)
    private String reviewAccount;

    /**
     * 评论截图url
     */
    @Column(name = "review_jt_url")
    private String reviewJtUrl;

    /**
     * 评论链接
     */
    @Column(name = "review_link_url")
    private String reviewLinkUrl;

    /**
     * 实际上评时间
     */
    @Column(name = "review_date_time")
    private String reviewDateTime;

    /**
     * Review图片
     */
    @Column(name = "review_img_url")
    private String reviewImgUrl;

    /**
     * Review视频
     */
    @Column(name = "review_video_url")
    private String reviewVideoUrl;

    /**
     * 是否反馈上评
     */
    @Column(name = "is_feedback_review")
    private String isFeedbackReview;

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
