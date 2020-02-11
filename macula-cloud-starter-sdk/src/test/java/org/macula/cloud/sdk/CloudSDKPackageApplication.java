package org.macula.cloud.sdk;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.macula.cloud.sdk.context.CloudApplicationContext;
import org.macula.cloud.sdk.entity.CurrentUserInfo;
import org.macula.cloud.sdk.event.BroadcastEvent;
import org.macula.cloud.sdk.event.InstanceProcessEvent;
import org.macula.cloud.sdk.utils.CurrentUserInfoUtils;
import org.macula.cloud.sdk.utils.J2CacheUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = { HibernateJpaAutoConfiguration.class, DataSourceAutoConfiguration.class })
@EnableDiscoveryClient
@EnableFeignClients
@RestController
public class CloudSDKPackageApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(CloudSDKPackageApplication.class, args);

		BroadcastEvent<?> broadcastEvent = BroadcastEvent.wrap(new TestBroadcastMessage("===========100========"));
		InstanceProcessEvent<?> instanceEvent = InstanceProcessEvent
				.wrap(new TestBroadcastMessage("===========100========"));

		J2CacheUtils.set(J2CacheUtils.CACHE_REGION, "my-broadcast-event", broadcastEvent);
		J2CacheUtils.set(J2CacheUtils.CACHE_REGION, "my-instance-event", instanceEvent);

		BroadcastEvent<?> broadcastValue = J2CacheUtils.get(J2CacheUtils.CACHE_REGION, "my-broadcast-event");
		InstanceProcessEvent<?> instanceValue = J2CacheUtils.get(J2CacheUtils.CACHE_REGION, "my-instance-event");
		System.out.println("====二级缓存==== broadcastEvent: " + broadcastValue + " instanceEvent: " + instanceValue);

		TestZookeeperLock testLock = CloudApplicationContext.getContainer().getBean(TestZookeeperLock.class);
		testLock.testZookeeperLock();

		CloudApplicationContext.getContainer().publishEvent(broadcastEvent);

	}

	@GetMapping("/current")
	private CurrentUserInfo success(HttpServletRequest request) {
		CurrentUserInfo info = CurrentUserInfoUtils.getCurrentUserInfo(request);
		return info;
	}

}
