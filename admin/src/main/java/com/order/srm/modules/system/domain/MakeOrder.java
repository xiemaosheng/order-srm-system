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
@Table(name = "srm_make_order")
public class MakeOrder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = MakeOrder.Update.class)
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
     * 下单人id
     */
    @Column(name = "make_user_id", nullable = false)
    private Long makeUserId;

    /**
     * 下单人名称
     */
    @Column(name = "make_user_name", nullable = false)
    private String makeUserName;

    /**
     * 购物卡id
     */
    @Column(name = "shop_card_id", nullable = false)
    private Long shopCardId;

    /**
     * 下单截图url
     */
    @Column(name = "make_jt_url")
    private String makeJtUrl;

    /**
     * 下单支付截图url
     */
    @Column(name = "make_payed_jt_url")
    private String makePayedJtUrl;

    /**
     * 支付金额
     */
    @Column(name ="pay_price")
    private double payPrice;

    /**
     * 系统自动选择地址
     */
    @Column(name ="auto_address")
    private String autoAddress;

    /**
     * 自用地址
     */
    @Column(name ="self_address")
    private String selfAddress;

    /**
     * 下单备注
     */
    @Column(name = "remark")
    private String remark;

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
