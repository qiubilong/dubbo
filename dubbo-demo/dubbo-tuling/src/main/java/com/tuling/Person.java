package com.tuling;

import com.tuling.car.Car;
import org.apache.dubbo.common.extension.SPI;

@SPI
public interface Person {

    Car getCar();
}
