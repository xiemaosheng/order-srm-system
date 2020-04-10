package com.order.srm.modules.system.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
*
* @date 2019-6-10 16:32:18
*/
@Data
public class CompanySmallDto implements Serializable {

    private Long id;

    private String name;

    private String code;
}