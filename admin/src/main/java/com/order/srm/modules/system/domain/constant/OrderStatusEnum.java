package com.order.srm.modules.system.domain.constant;

import java.util.HashMap;
import java.util.Map;

public enum OrderStatusEnum {
    NOT_ORDER(0L, "未下单"),

    WAIT_FEEDBACK_ORDER(1L, "待反馈(已下单)"),

    WAIT_SIGNED_ORDER(2L, "待签收"),

    WAIT_REVIEW_ORDER(3L, "待上评"),

    WAIT_CONFIRM_REVIEWED_ORDER(4L, "待确认(已上评)"),

    WAIT_FEEDBACK_REVIEWED_ORDER(5L, "待反馈(已上评)"),

    WAIT_PAYED_ORDER(6L, "待收款"),

    PAYED_ORDER(7L, "已收款"),

    WAIT_DELIVER_ORDER(8L, "等待交付"),

    DELIVER_ORDER(9L, "已交付"),

    PASS_ORDER(10L, "已审核"),

    ;

    private Long status;

    private String text;

    private static Map<Long, OrderStatusEnum> map;

    static {
        map = new HashMap<>();
        for (OrderStatusEnum item : OrderStatusEnum.values()) {
            map.put(item.getStatus(), item);
        }
    }

    OrderStatusEnum(Long status, String text) {
        this.status = status;
        this.text = text;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static OrderStatusEnum get(Long status) {
        return map.get(status);
    }
}
