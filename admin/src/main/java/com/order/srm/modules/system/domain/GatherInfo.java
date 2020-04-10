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
@Table(name = "srm_gather_info")
public class GatherInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = GatherInfo.Update.class)
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
     * 收款人id
     */
    @Column(name = "gather_user_id", nullable = false)
    private Long gatherUserId;

    /**
     * 收款人名称
     */
    @Column(name = "gather_user_name", nullable = false)
    private String gatherUserName;

    /**
     * 收款截图url
     */
    @Column(name = "gather_jt_url")
    private String gatherJtUrl;

    /**
     * 收款时间
     */
    @Column(name = "gather_date_time")
    private String gatherDateTime;

    /**
     * 收款金额
     */
    @Column(name = "gather_price")
    private double gatherPrice;

    /**
     * 收款佣金
     */
    @Column(name = "gather_commission")
    private String gatherCommission;

    /**
     * 是否可以收款
     */
    @Column(name = "is_can_gather")
    private String isCanGather;

    /**
     * 是否已经收款
     */
    @Column(name = "is_gather")
    private String isGather;

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
