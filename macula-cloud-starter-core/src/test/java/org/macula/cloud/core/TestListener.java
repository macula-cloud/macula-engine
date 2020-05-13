package org.macula.cloud.core;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Auther: Aaron
 * @Date: 2019/1/16 11:42
 * @Description:
 */
@Component
public class TestListener  implements ApplicationListener<TestBroadcastMessage>{


    @Override
    public void onApplicationEvent(TestBroadcastMessage testBroadcastMessage) {
        System.out.println("=============TestListener============"+testBroadcastMessage.getSource());
    }
}
