/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.demo.consumer.comp;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.demo.DemoService;

import org.apache.dubbo.demo.v2.OrderQuery;
import org.apache.dubbo.demo.v2.OrderQueryResp;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component("demoServiceComponent")
public class DemoServiceComponent  {
    @DubboReference(version = "1.0.1", group = "test-xx", mock = "fail: return 123",timeout = 1000 * 60,retries = 0, check = false, lazy = true) //内部rpc异常
    private DemoService demoService;

    public OrderQueryResp queryOrder(OrderQuery query) {
        return demoService.queryOrder(query);
    }

    public String sayHello(String name) {
        return demoService.sayHello(name);
    }

    public CompletableFuture<String> sayHelloAsync(String name) {
        return null;
    }
}
