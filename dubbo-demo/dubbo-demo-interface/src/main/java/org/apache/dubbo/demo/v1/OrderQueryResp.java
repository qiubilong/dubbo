package org.apache.dubbo.demo.v1;


import java.io.Serializable;

import lombok.Data;

public class OrderQueryResp implements Serializable {

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    private String orderCode;

    public OrderQueryResp() {
    }

    public OrderQueryResp(String orderCode) {
        this.orderCode = orderCode;
    }
}
