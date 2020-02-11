package org.macula.cloud.sdk.lock.distributedlock;

import org.apache.curator.framework.recipes.locks.InterProcessLock;

/**
 * Zookeeper上下文
 */
public class ZookeeperLockContext {

	private static final ThreadLocal<InterProcessLock> LOCK_CONTEXT = new ThreadLocal<>();

	public static InterProcessLock getContext() {
		return LOCK_CONTEXT.get();
	}

	public static void setContext(InterProcessLock interProcessLock) {
		LOCK_CONTEXT.set(interProcessLock);
	}

	public static void remove() {
		LOCK_CONTEXT.remove();
	}
}
