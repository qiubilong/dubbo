package org.apache.dubbo.demo.v2;

import java.io.Serializable;

import lombok.Data;

public class OrderQuery implements Serializable {

    private String orderCode;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

}
