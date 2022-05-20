//package org.macula.cloud.core.servlet;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Date;
//import java.util.Enumeration;
//import java.util.HashSet;
//import java.util.List;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.macula.cloud.api.context.CloudApplicationContext;
//import org.macula.engine.core.domain.AccessLog;
//import org.macula.cloud.core.event.AccessLogEvent;
//import org.macula.cloud.core.event.InstanceProcessEvent;
//import org.macula.cloud.core.principal.SubjectPrincipal;
//import org.macula.cloud.core.utils.ExceptionUtils;
//import org.macula.cloud.core.utils.HttpRequestUtils;
//import org.macula.cloud.core.utils.SecurityUtils;
//import org.macula.cloud.core.utils.StringUtils;
//import org.macula.engine.assistant.support.ApplicationId;
//
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.security.web.util.matcher.OrRequestMatcher;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import lombok.extern.slf4j.Slf4j;
//import reactor.core.publisher.Mono;
//
//@Slf4j
//public class RequestAccessLogFilter extends OncePerRequestFilter {
//
//	private RequestMatcher ignoreRequestMatcher;
//
//	public RequestAccessLogFilter(List<String> paths) {
//		List<RequestMatcher> matchers = new ArrayList<>();
//		for (String pattern : paths) {
//			matchers.add(new AntPathRequestMatcher(pattern));
//		}
//		this.ignoreRequestMatcher = new OrRequestMatcher(matchers);
//	}
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//
//		if (ignoreRequestMatcher != null && ignoreRequestMatcher.matches(request)) {
//			super.doFilter(request, response, filterChain);
//			return;
//		}
//
//		long start = System.currentTimeMillis();
//		Throwable exception = null;
//		try {
//			super.doFilter(request, response, filterChain);
//		} catch (Throwable ex) {
//			exception = ex;
//			throw ex;
//		} finally {
//			long end = System.currentTimeMillis();
//			createAccessLog(request, response, start, end, exception).subscribe(accessLog -> {
//				log.trace("Created AccessLog: {} , and publish...", accessLog);
//				InstanceProcessEvent<AccessLogEvent> event = InstanceProcessEvent.wrap(new AccessLogEvent(accessLog));
//				CloudApplicationContext.getContainer().publishEvent(event);
//			});
//		}
//	}
//
//	public Mono<AccessLog> createAccessLog(HttpServletRequest request, HttpServletResponse response, long start, long end, Throwable exception) {
//		SubjectPrincipal subjectPrincipal = SecurityUtils.getSubjectPrincipal();
//		return Mono.justOrEmpty(subjectPrincipal).map(principal -> {
//			AccessLog accesslog = new AccessLog();
//			accesslog.setSubject(principal.getName());
//			accesslog.setSubjectType(principal.getType());
//			accesslog.setStartTime(new Date(start));
//			accesslog.setEndTime(new Date(end));
//			accesslog.setApplication(ApplicationId.current().getApplicationId());
//			accesslog.setInstance(ApplicationId.current().getInstanceId());
//			accesslog.setRequestAddress(HttpRequestUtils.getRequestAddress(request));
//			accesslog.setRequestURL(request.getRequestURL().toString());
//			accesslog.setResponseCode(String.valueOf(response.getStatus()));
//			if (exception != null) {
//				accesslog.setErrorMessage(ExceptionUtils.getStackTrace(exception));
//			}
//			Collection<String> sb = new HashSet<String>();
//			for (LogOption logOption : LogOption.values()) {
//				sb.add(LogOption.NEW_LINE);
//				sb.add(logOption.getProfile(request, response));
//			}
//			accesslog.setRequestDetail(StringUtils.join(sb, LogOption.NEW_LINE));
//			return accesslog;
//		});
//	}
//
//	static enum LogLevel {
//
//		ANY(0), LOGON(10), ERROR(90), NONE(100);
//
//		private int value;
//
//		private LogLevel(int value) {
//			this.value = value;
//		}
//
//		public static Collection<LogLevel> getLevels(int value) {
//			Collection<LogLevel> result = new ArrayList<LogLevel>();
//			LogLevel[] levels = LogLevel.values();
//			for (int i = 0; i < levels.length; i++) {
//				if (levels[i].value >= value) {
//					result.add(levels[i]);
//				}
//			}
//			return result;
//		}
//
//		public boolean needLog(LogLevel level) {
//			return level.value >= this.value;
//		}
//	}
//
//	static enum LogOption {
//
//		REQUEST_HEAD(1) {
//
//			@Override
//			public String getProfile(HttpServletRequest request, HttpServletResponse response) {
//				List<String> headerList = new ArrayList<String>();
//				headerList.add("================ REQUEST HEAD =================");
//				Enumeration<String> headers = request.getHeaderNames();
//				while (headers.hasMoreElements()) {
//					String headerName = headers.nextElement();
//					String headerValue = request.getHeader(headerName);
//					headerList.add(getConcat(headerName, headerValue));
//				}
//				return StringUtils.join(headerList, NEW_LINE);
//			}
//
//		},
//		REQUEST_PARAM(2) {
//
//			@Override
//			public String getProfile(HttpServletRequest request, HttpServletResponse response) {
//				List<String> paramList = new ArrayList<String>();
//				paramList.add("================ REQUEST PARAMETER =================");
//				Enumeration<String> params = request.getParameterNames();
//				while (params.hasMoreElements()) {
//					String param = params.nextElement();
//					// password is secret
//					if (param.toLowerCase().indexOf("password") >= 0) {
//						paramList.add(getConcat(param, "******"));
//					} else {
//						Object paramValue = request.getParameter(param);
//						paramList.add(getConcat(param, paramValue));
//					}
//				}
//				return StringUtils.join(paramList, NEW_LINE);
//			}
//
//		},
//		SESSION_ATTR(4) {
//
//			@Override
//			public String getProfile(HttpServletRequest request, HttpServletResponse response) {
//				List<String> attrList = new ArrayList<String>();
//				attrList.add("================ SESSION ATTRIBUTE =================");
//				HttpSession session = request.getSession(false);
//				if (session != null) {
//					Enumeration<String> attrs = session.getAttributeNames();
//					while (attrs.hasMoreElements()) {
//						String attr = attrs.nextElement();
//						Object attrValue = session.getAttribute(attr);
//						attrList.add(getConcat(attr, attrValue));
//					}
//				}
//				return StringUtils.join(attrList, NEW_LINE);
//			}
//
//		},
//		REQUEST_ATTR(8) {
//
//			@Override
//			public String getProfile(HttpServletRequest request, HttpServletResponse response) {
//				List<String> attrList = new ArrayList<String>();
//				attrList.add("================ REQUEST ATTRIBUTE =================");
//
//				Enumeration<String> attrs = request.getAttributeNames();
//				while (attrs.hasMoreElements()) {
//					String attr = attrs.nextElement();
//					Object attrValue = request.getAttribute(attr);
//					attrList.add(getConcat(attr, attrValue));
//				}
//				return StringUtils.join(attrList, NEW_LINE);
//			}
//
//		};
//
//		private static final String NEW_LINE = "\r\n";
//		private int value;
//
//		private LogOption(int value) {
//			this.value = value;
//		}
//
//		public abstract String getProfile(HttpServletRequest request, HttpServletResponse response);
//
//		public static Collection<LogOption> getOptions(int value) {
//			Collection<LogOption> result = new ArrayList<LogOption>();
//			LogOption[] options = LogOption.values();
//			for (int i = 0; i < options.length; i++) {
//				if ((options[i].value & value) == options[i].value) {
//					result.add(options[i]);
//				}
//			}
//			return result;
//		}
//
//		private static String getConcat(Object key, Object value) {
//			return key + " : " + value;
//		}
//	}
//
//	public void setIgnoreRequestMatcher(RequestMatcher ignoreRequestMatcher) {
//		this.ignoreRequestMatcher = ignoreRequestMatcher;
//	}
//}
