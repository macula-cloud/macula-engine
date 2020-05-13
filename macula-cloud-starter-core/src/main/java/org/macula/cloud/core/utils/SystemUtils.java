package org.macula.cloud.core.utils;

import java.time.Instant;
import java.util.Date;

public final class SystemUtils {

	private static long DB_TIME_GAP = 0;

	/**
	 * 获取当前时间.
	 */
	public static Date getCurrentTime() {
		return new Date(System.currentTimeMillis() + DB_TIME_GAP);
	}

	/**
	 * 获取当前时间.
	 */
	public static Instant getCurrentInstant() {
		return Instant.ofEpochMilli(System.currentTimeMillis() + DB_TIME_GAP);
	}

	public static final void setTimeGap(long timeGap) {
		DB_TIME_GAP = timeGap;
	}

}
