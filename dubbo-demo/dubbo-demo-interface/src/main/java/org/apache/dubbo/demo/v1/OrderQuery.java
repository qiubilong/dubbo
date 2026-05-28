package org.apache.dubbo.demo.v1;


import java.io.Serializable;


public class OrderQuery implements Serializable {

    private String orderCode;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
}
