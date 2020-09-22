package org.macula.cloud.core;

import java.io.IOException;

import org.macula.cloud.api.context.CloudApplicationContext;
import org.macula.cloud.cache.utils.J2CacheUtils;
import org.macula.cloud.core.event.BroadcastEvent;
import org.macula.cloud.core.event.InstanceProcessEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = { HibernateJpaAutoConfiguration.class, DataSourceAutoConfiguration.class })
@EnableDiscoveryClient
@EnableFeignClients
@RestController
public class CorePackageApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(CorePackageApplication.class, args);

		BroadcastEvent<?> broadcastEvent = BroadcastEvent.wrap(new TestBroadcastMessage("===========100========"));
		InstanceProcessEvent<?> instanceEvent = InstanceProcessEvent.wrap(new TestBroadcastMessage("===========100========"));

		J2CacheUtils.set(J2CacheUtils.CACHE_REGION, "my-broadcast-event", broadcastEvent);
		J2CacheUtils.set(J2CacheUtils.CACHE_REGION, "my-instance-event", instanceEvent);

		BroadcastEvent<?> broadcastValue = J2CacheUtils.get(J2CacheUtils.CACHE_REGION, "my-broadcast-event");
		InstanceProcessEvent<?> instanceValue = J2CacheUtils.get(J2CacheUtils.CACHE_REGION, "my-instance-event");
		System.out.println("====二级缓存==== broadcastEvent: " + broadcastValue + " instanceEvent: " + instanceValue);

		TestRedisLock testLock = CloudApplicationContext.getContainer().getBean(TestRedisLock.class);
		String result = testLock.testRedisLock();
		System.out.println("TestLock get Result: " + result);
		CloudApplicationContext.getContainer().publishEvent(broadcastEvent);

	}

}
