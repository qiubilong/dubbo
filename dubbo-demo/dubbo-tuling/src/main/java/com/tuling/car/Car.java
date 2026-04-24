package com.tuling.car;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;
@SPI("black") /* 标记拓展类 - extensionLoader.getExtension("true")时获取默认实现类 - black */
public interface Car {

    @Adaptive("carType") /* URL 参数自适应 寻找拓展类 */
    String getCarName(URL url);

    String sayHell();


}
