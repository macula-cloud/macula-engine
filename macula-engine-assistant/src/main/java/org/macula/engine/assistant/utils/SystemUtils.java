package org.macula.engine.assistant.utils;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public final class SystemUtils {

	private static long DB_TIME_GAP = 0;

	/**
	 * 获取当前时间.
	 */
	public static Date getCurrentTime() {
		return new Date(System.currentTimeMillis() + DB_TIME_GAP);
	}

	public static Date getDate(long timestamp) {
		return new Date(timestamp + DB_TIME_GAP);
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

	public static final Date getNextZeroPoint() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getCurrentTime());
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

}
