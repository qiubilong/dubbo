package com.tuling.car;

import com.tuling.Person;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Protocol;

import java.util.ServiceLoader;

public class SpiTestCar {
    public static void main(String[] args) {


/*
        ExtensionLoader<Protocol> extensionLoader = ExtensionLoader.getExtensionLoader(Protocol.class);
        Protocol protocol = extensionLoader.getExtension("http");
        System.out.println(protocol);*/


        URL url = new URL("http://", "localhost", 8080);
        ExtensionLoader<Car> extensionLoader = ExtensionLoader.getExtensionLoader(Car.class);
        Car carDefault = extensionLoader.getExtension("true");//@SPI指定 默认拓展类
        System.out.println(carDefault.sayHell());

        Car car = extensionLoader.getExtension("red");//根据名字查找 拓展类
        System.out.println(car.sayHell());

        Car carAdaptive = extensionLoader.getAdaptiveExtension();/* 动态自适应实现类 */
        url = url.addParameter("carType", "black");
        System.out.println(carAdaptive.getCarName(url));
        System.out.println(carAdaptive.sayHell());

    }
}
