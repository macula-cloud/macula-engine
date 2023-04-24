package org.macula.engine.login.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

public enum LogOption {

	REQUEST_HEAD(1) {

		@Override
		public String getProfile(HttpServletRequest request, HttpServletResponse response) {
			List<String> headerList = new ArrayList<String>();
			headerList.add("================ REQUEST HEAD =================");
			Enumeration<String> headers = request.getHeaderNames();
			while (headers.hasMoreElements()) {
				String headerName = headers.nextElement();
				String headerValue = request.getHeader(headerName);
				headerList.add(getConcat(headerName, headerValue));
			}
			return StringUtils.join(headerList, NEW_LINE);
		}

	},
	REQUEST_PARAM(2) {

		@Override
		public String getProfile(HttpServletRequest request, HttpServletResponse response) {
			List<String> paramList = new ArrayList<String>();
			paramList.add("================ REQUEST PARAMETER =================");
			Enumeration<String> params = request.getParameterNames();
			while (params.hasMoreElements()) {
				String param = params.nextElement();
				// password is secret
				if (param.toLowerCase().indexOf("password") >= 0) {
					paramList.add(getConcat(param, "******"));
				} else {
					Object paramValue = request.getParameter(param);
					paramList.add(getConcat(param, paramValue));
				}
			}
			return StringUtils.join(paramList, NEW_LINE);
		}

	},
	SESSION_ATTR(4) {

		@Override
		public String getProfile(HttpServletRequest request, HttpServletResponse response) {
			List<String> attrList = new ArrayList<String>();
			attrList.add("================ SESSION ATTRIBUTE =================");
			HttpSession session = request.getSession(false);
			if (session != null) {
				Enumeration<String> attrs = session.getAttributeNames();
				while (attrs.hasMoreElements()) {
					String attr = attrs.nextElement();
					Object attrValue = session.getAttribute(attr);
					attrList.add(getConcat(attr, attrValue));
				}
			}
			return StringUtils.join(attrList, NEW_LINE);
		}

	},
	REQUEST_ATTR(8) {

		@Override
		public String getProfile(HttpServletRequest request, HttpServletResponse response) {
			List<String> attrList = new ArrayList<String>();
			attrList.add("================ REQUEST ATTRIBUTE =================");

			Enumeration<String> attrs = request.getAttributeNames();
			while (attrs.hasMoreElements()) {
				String attr = attrs.nextElement();
				Object attrValue = request.getAttribute(attr);
				attrList.add(getConcat(attr, attrValue));
			}
			return StringUtils.join(attrList, NEW_LINE);
		}

	};

	private int value;

	private LogOption(int value) {
		this.value = value;
	}

	public abstract String getProfile(HttpServletRequest request, HttpServletResponse response);

	public static Collection<LogOption> getOptions(int value) {
		Collection<LogOption> result = new ArrayList<LogOption>();
		LogOption[] options = LogOption.values();
		for (int i = 0; i < options.length; i++) {
			if ((options[i].value & value) == options[i].value) {
				result.add(options[i]);
			}
		}
		return result;
	}

	private static final String NEW_LINE = "\r\n";

	private static String getConcat(Object key, Object value) {
		return key + " : " + value;
	}
}