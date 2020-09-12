package org.macula.cloud.core.configure;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.macula.cloud.core.configure.model.ZookeeperProperties;
import org.macula.cloud.core.lock.aop.ZookeeperLockAop;
import org.macula.cloud.core.lock.distributedlock.ZookeeperDistributedLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分布式锁自动配置器
 */
@Configuration
@ConditionalOnProperty(prefix = "macula.cloud", name = "zookeeper.lock", havingValue = "true", matchIfMissing = false)
public class CloudZookeeperLockConfigure {

	@Bean
	public ZookeeperDistributedLock zookeeperDistributedLock(CuratorFramework curatorFramework) {
		return new ZookeeperDistributedLock(curatorFramework);
	}

	@Bean
	public ZookeeperLockAop zookeeperAop(ZookeeperDistributedLock zookeeperDistributedLock) {
		return new ZookeeperLockAop(zookeeperDistributedLock);
	}

	@Bean(destroyMethod = "close")
	public CuratorFramework curatorFramework(CoreConfigurationProperties sdkProperties) {
		ZookeeperProperties zookeeperProperties = sdkProperties.getZookeeper();
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
