package com.yjtech.wisdom.tourism.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * 订单状态,
 * 1-待支付, 2-已取消, 3-支付中, 4-支付失败, 5-已支付, 6-退款申请中, 7-退款中, 8-退款失败,9-已退款
 * 10-待出票,11-待检票,12-已检票，21-已过期
 * 13-待确认,14-待入住,15-已入住
 * 16-待发货,17-已发货,18-已收货
 * 19-待使用,20-已使用（21已被使用）
 *
 * @author liuhong
 */
public enum OrderStatusEnum {
    /**
     * 各产品订单状态
     */
    TO_BE_PAY((byte) 1, "待支付"),
    CANCEL((byte) 2, "已取消"),
    PAYING((byte) 3, "支付中"),
    PAY_FAILED((byte) 4, "支付失败"),
    PAID((byte) 5, "已支付"),
    REFUND_APPLY((byte) 6, "退款申请中"),
    REFUNDING((byte) 7, "退款中"),
    REFUND_FAILED((byte) 8, "退款失败"),
    REFUNDED((byte) 9, "已退款"),

    WAITING_FOR_TICKET((byte) 10, "待出票"),
    TO_BE_USED((byte) 11, "待检票"),
    CHECKED((byte) 12, "已检票"),

    TO_BE_CONFIRMED((byte) 13, "待确认"),
    STAY_IN((byte) 14, "待入住"),
    CHECKED_IN((byte) 15, "已入住"),

    TO_BE_DELIVERED((byte) 16, "待发货"),
    SHIPPED((byte) 17, "已发货"),
    RECEIVED((byte) 18, "已收货"),

    COUPON_TO_BE_USED((byte) 19, "待使用"),
    COUPON_USED((byte) 20, "已使用"),

    EXPIRED_TICKET((byte) 21, "已过期"),

    EVALUATE_FINISHED((byte)22, "已完成"),


    ;
    //用户有效订单状态
    public static List<Byte> USER_VALID_ORDER = Arrays.asList(
            PAYING.getStatus(), PAID.getStatus(), REFUND_APPLY.getStatus(), REFUNDING.getStatus(), REFUND_FAILED.getStatus(),
            WAITING_FOR_TICKET.getStatus(), TO_BE_USED.getStatus(), EXPIRED_TICKET.getStatus(), CHECKED.getStatus(),
            TO_BE_CONFIRMED.getStatus(), STAY_IN.getStatus(), CHECKED_IN.getStatus(),
            TO_BE_DELIVERED.getStatus(), SHIPPED.getStatus(), RECEIVED.getStatus(),
            COUPON_TO_BE_USED.getStatus(), COUPON_USED.getStatus());

    //不返回票码的状态
    public static byte[] NOT_RETURN_CODE = {TO_BE_PAY.getStatus(), PAYING.getStatus(), CANCEL.getStatus(), PAY_FAILED.getStatus(),REFUNDED.getStatus()};
    //兑换码不能使用
    public static byte[] CODE_NOT_CAN_USE = {REFUND_APPLY.getStatus(), REFUNDING.getStatus(), REFUNDED.getStatus(), CHECKED.getStatus(), COUPON_USED.getStatus()};
    //能处理支付通知的订单状态
    private static byte[] CAN_PAY_NOTIFY = {TO_BE_PAY.getStatus(), PAYING.getStatus(), PAY_FAILED.getStatus()};
    //商户能主动拒绝订单的状态，会触发退款操作
    private static byte[] MERCHANT_REFUSE_ORDER_REFUND = {TO_BE_CONFIRMED.getStatus(), TO_BE_DELIVERED.getStatus()};
    //票码已经检过的状态
    private static byte[] TICKET_CHECKED = {CHECKED.getStatus(), COUPON_USED.getStatus()};
    //后台操作可退的订单状态
    public static byte[] BACK_CAN_REFUND_STATUS = {REFUND_FAILED.getStatus(),
            TO_BE_USED.getStatus(), EXPIRED_TICKET.getStatus(), CHECKED.getStatus(),
            TO_BE_CONFIRMED.getStatus(), STAY_IN.getStatus(),
            TO_BE_DELIVERED.getStatus(), SHIPPED.getStatus(),
            COUPON_TO_BE_USED.getStatus()};

    @Getter
    private byte status;
    @Getter
    private String message;

    OrderStatusEnum(byte status, String message) {
        this.status = status;
        this.message = message;
    }

    public static String parseMessage(byte st) {
        return parseOrderStatus(st).message;
    }

    public static OrderStatusEnum parseOrderStatus(byte st) {
        for (OrderStatusEnum orderStatus : OrderStatusEnum.values()) {
            if (orderStatus.status == st) {
                return orderStatus;
            }
        }
        return null;
    }

}
