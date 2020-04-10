package com.order.srm.modules.system.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "srm_company")
public class Company implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = Update.class)
    private Long id;

    @Column(name = "enabled", nullable = false)
    @NotNull
    private Boolean enabled;

    /**
     * 公司编码
     */
    @Column(name = "code", nullable = false)
    @NotBlank
    private String code;

    /**
     * 公司名称
     */
    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    public @interface Update {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(id, company.id) &&
                Objects.equals(enabled, company.enabled) &&
                Objects.equals(code, company.code) &&
                Objects.equals(name, company.name) &&
                Objects.equals(createTime, company.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, enabled, code, name, createTime);
    }
}
