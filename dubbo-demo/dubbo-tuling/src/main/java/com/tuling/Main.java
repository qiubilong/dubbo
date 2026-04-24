package com.tuling;

import com.tuling.car.RedCar;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.tuling")
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Main.class);
        RedCar redCar = applicationContext.getBean("redCar", RedCar.class);
        redCar.sayHell();
        System.out.println(redCar);

    }
}
