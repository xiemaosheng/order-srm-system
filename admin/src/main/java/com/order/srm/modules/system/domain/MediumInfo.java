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
@Table(name = "srm_mediumInfo")
public class MediumInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = MediumInfo.Update.class)
    private Long id;

    /**
     * 公司id
     */
    @Column(name = "company_id", nullable = false)
    private Long companyId;

    /**
     * 标签
     */
    @Column(name = "tag", nullable = false)
    @NotBlank
    private String tag;


    /**
     * 微信
     */
    @Column(name = "weixin", nullable = false)
    @NotBlank
    private String weixin;

    /**
     * 添加人id
     */
    @Column(name = "addUserId", nullable = false)
    private Long addUserId;

    /**
     * 备注信息
     */
    @Column(name = "remark", nullable = false)
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    public @interface Update {
    }

}
