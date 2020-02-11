package org.macula.cloud.sdk.configure;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.macula.cloud.sdk.configure.model.SDKZookeeperProperties;
import org.macula.cloud.sdk.lock.aop.ZookeeperLockAop;
import org.macula.cloud.sdk.lock.distributedlock.ZookeeperDistributedLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分布式锁自动配置器
 */
@Configuration
@ConditionalOnProperty(prefix = "macula.cloud", name = "zookeeper.lock", havingValue = "true", matchIfMissing = false)
public class CloudLockConfigure {

	@Bean
	public ZookeeperDistributedLock zookeeperDistributedLock(CuratorFramework curatorFramework) {
		return new ZookeeperDistributedLock(curatorFramework);
	}

	@Bean
	public ZookeeperLockAop zookeeperAop(ZookeeperDistributedLock zookeeperDistributedLock) {
		return new ZookeeperLockAop(zookeeperDistributedLock);
	}

	@Bean(destroyMethod = "close")
	public CuratorFramework curatorFramework(SDKConfigurationProperties sdkProperties) {
		SDKZookeeperProperties zookeeperProperties = sdkProperties.getZookeeper();
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(zookeeperProperties.getBaseSleepTimeMs(),
				zookeeperProperties.getMaxRetries());
		CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
				.connectString(zookeeperProperties.getZkServers())
				.sessionTimeoutMs(zookeeperProperties.getSessionTimeout())
				.connectionTimeoutMs(zookeeperProperties.getConnectionTimeout()).retryPolicy(retryPolicy).build();
		curatorFramework.start();
		return curatorFramework;
	}

}
