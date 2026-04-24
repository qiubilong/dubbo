package com.tuling;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Protocol;

public class TestSpiProtocol {
    public static void main(String[] args) {



        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();

        System.out.println(protocol);
    }
}
