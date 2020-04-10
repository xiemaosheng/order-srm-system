package com.order.srm.modules.system.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @date 2018-11-23
 */
@Data
public class RoleSmallDto implements Serializable {

    private Long id;

    private String name;

    private Integer level;

    private String dataScope;
}
