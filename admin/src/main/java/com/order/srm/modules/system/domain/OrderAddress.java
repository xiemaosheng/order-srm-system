package com.order.srm.modules.system.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "srm_order_address")
public class OrderAddress implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = OrderAddress.Update.class)
    private Long id;

    /**
     * 公司id
     */
    @Column(name = "company_id", nullable = false)
    private Long companyId;

    /**
     * 站点
     */
    @Column(name = "website", nullable = false)
    @NotBlank
    private String website;

    /**
     * 地址名称
     */
    @Column(name = "address_name", nullable = false)
    @NotBlank
    private String addressName;

    /**
     * 地址
     */
    @Column(name = "address", nullable = false)
    @NotBlank
    private String address;

    /**
     * 备注信息
     */
    @Column(name = "remark", nullable = false)
    private String remark;

    /**
     * 是否启用
     */
    @Column(name = "enabled", nullable = false)
    @NotNull
    private Boolean enabled;

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
