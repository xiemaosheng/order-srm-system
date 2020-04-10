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
@Table(name = "srm_order")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = Order.Update.class)
    private Long id;

    /**
     * 公司id
     */
    @Column(name = "company_id", nullable = false)
    private Long companyId;

    /**
     * 店铺名称
     */
    @Column(name = "mkt_name", nullable = false)
    private String mktName;

    /**
     * 商品图片地址
     */
    @Column(name = "good_img_url", nullable = false)
    private String goodImgUrl;

    /**
     * 商品订单号
     */
    @Column(name = "good_order_num")
    private String goodOrderNum;

    /**
     * 接单人账号id
     */
    @Column(name = "rec_user_id", nullable = false)
    private Long recUserId;

    /**
     * 接单人账号名称
     */
    @Column(name = "rec_user_name", nullable = false)
    private String recUserName;

    /**
     * 数量
     */
    @Column(name = "order_count")
    private int orderCount;

    /**
     * 天数
     */
    @Column(name = "day_count")
    private int dayCount;

    /**
     * 工作性质
     */
    @Column(name = "worker_type", nullable = false)
    private String workerType;

    /**
     * 卖家联系方式：微信/QQ
     */
    @Column(name = "seller_contact_type", nullable = false)
    private String sellerContactType;

    /**
     * 卖家订单类型
     */
    @Column(name = "seller_order_type", nullable = false)
    private String sellerOrderType;

    /**
     * 返款方式
     */
    @Column(name = "cash_back_type")
    private int cashBackType;

    /**
     * 站点
     */
    @Column(name = "website")
    private String website;

    /**
     *
     */
    @Column(name = "ASIN")
    private String ASIN;

    /**
     * 下单要求
     */
    @Column(name = "xd_requirement")
    private String xdRequirement;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 追踪号
     */
    @Column(name = "track_no")
    private String trackNo;

    /**
     * 评论标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 评论内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 接单日期
     */
    @Column(name = "rec_date_time")
    private String recDateTime;

    /**
     * 计划上评日期
     */
    @Column(name = "plan_review_date_time")
    private String planReviewDateTime;

    /**
     * 接单金额
     */
    @Column(name = "rec_price")
    private double recPrice;

    /**
     * 接单佣金
     */
    @Column(name = "rec_commission")
    private double recCommission;

    /**
     * 回收佣金
     */
    @Column(name = "recover_commission")
    private double recoverCommission;

    /**
     * 订单状态
     */
    @Column(name = "status")
    private Long status = 0L;

    /**
     * 是否留评
     */
    @Column(name = "is_comment")
    private String isComment;

    /**
     * 是否需要提供到货拍照
     */
    @Column(name = "is_photo")
    private String isPhoto;

    /**
     * 是否换仓
     */
    @Column(name = "is_bin_shift")
    private String isBinShift;

    /**
     * 是否回收
     */
    @Column(name = "is_recover")
    private String isRecover;

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
