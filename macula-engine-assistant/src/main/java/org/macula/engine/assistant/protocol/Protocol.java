package org.macula.engine.assistant.protocol;

/**
 * <p> Protocol Enum </p>
 */
public enum Protocol {
	/**
	 * 协议类型
	 */
	HTTP("http://", "http"),
	HTTPS("https://", "https");

	private final String format;
	private final String prefix;

	Protocol(String format, String prefix) {
		this.format = format;
		this.prefix = prefix;
	}

	public String getFormat() {
		return format;
	}

	public String getPrefix() {
		return prefix;
	}
}
