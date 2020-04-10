package com.order.srm.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @date 2019-09-05
 */
@Data
public class LocalStorageDto implements Serializable {

    private Long id;

    private String realName;

    private String name;

    private String url;

    private String suffix;

    private String type;

    private String size;

    private String operate;

    private Timestamp createTime;
}