package com.tuling.car;

import org.apache.dubbo.common.URL;
/* 最终实现类  - AOP包装类 */
public class CarWrapper implements Car {

    private Car car; /* 内部SPI具体实现类 */

    public CarWrapper(Car car) {
        this.car = car;
    }

    @Override
    public String getCarName(URL url) {
        System.out.println("wrapper...url="+url);
        return car.getCarName(url);
    }

    @Override
    public String sayHell() {
        return "wrapper -->"+ car.sayHell();
    }
}
