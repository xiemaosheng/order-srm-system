package com.order.srm.modules.system.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "srm_shop_card")
public class ShopCard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = ShopCard.Update.class)
    private Long id;

    /**
     * 公司id
     */
    @Column(name = "company_id")
    private Long companyId;

    /**
     * 购物卡卡号id
     */
    @Column(name = "shop_card_id")
    private String shopCardId;

    /**
     * 购物卡账号
     */
    @Column(name = "shop_card_account")
    private String shopCardAccount;

    /**
     * 购物卡账号密码
     */
    @Column(name = "shop_card_password")
    private String shopCardPassword;

    /**
     * 购物卡 持卡人姓名
     */
    @Column(name = "shop_card_user_name")
    private String shopCardUserName;

    /**
     * 站点
     */
    @Column(name = "website")
    private String website;

    /**
     * 账号类型
     */
    @Column(name = "shop_card_type")
    private String shopCardType;

    /**
     * 状态
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 是否可上评
     */
    @Column(name = "is_can_comment")
    private String isCanComment;

    /**
     * 初始金额
     */
    @Column(name = "init_amount")
    private Double initAmount;

    /**
     * 总余额
     */
    @Column(name = "total_amount")
    private Double totalAmount;

    /**
     * 账号余额
     */
    @Column(name = "account_amount")
    private Double accountAmount;

    /**
     * 升级时间
     */
    @Column(name = "upgrade_time")
    private Date upgradeTime;

    /**
     * 到期时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 手机
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 辅助邮箱
     */
    @Column(name = "help_email")
    private String helpEmail;

    /**
     * 学生邮箱
     */
    @Column(name = "student_email")
    private String studentEmail;

    /**
     * 学生邮箱密码
     */
    @Column(name = "student_email_pwd")
    private String studentEmailPwd;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 创建人id
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 创建人名称
     */
    @Column(name = "create_user_name")
    private String createUserName;

    /**
     * 修改人id
     */
    @Column(name = "update_user_id")
    private Long updateUserId;

    /**
     * 修改人名称
     */
    @Column(name = "update_user_name")
    private String updateUserName;

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
