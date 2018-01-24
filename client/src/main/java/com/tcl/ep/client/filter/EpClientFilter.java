package com.tcl.ep.client.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcl.ep.client.EPClient;
import com.tcl.ep.client.util.ClientConfiguration;

/**
 * Created by panmin on 16-11-16.
 */
public class EpClientFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(EpClientFilter.class);
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	

	private String epClientPrefix;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		configClient(filterConfig);
		setEPClientPrefix(filterConfig);
	}

	private void configClient(FilterConfig filterConfig) {
		
		String projectId = filterConfig.getInitParameter("projectId").trim();
		try {
			ClientConfiguration.setProjectId(Long.valueOf(projectId));
		} catch (Exception e) {
			logger.error("the projectId must be provided !!!!");
		}
		ClientConfiguration.setModule(filterConfig.getInitParameter("module").trim());
	}

	private void setEPClientPrefix(FilterConfig filterConfig) {
		String contextPath = filterConfig.getServletContext().getContextPath();
		epClientPrefix = contextPath + ClientConfiguration.getEpClientUrlPrefix();
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException,
			ServletException {
		this.request = (HttpServletRequest) servletRequest;
		this.response = ((HttpServletResponse) servletResponse);

		if (isEpClientRequest(request)) {
			dispatch2EPClient(request, response);
			return;
		}

		try {
			chain.doFilter(servletRequest, servletResponse);
		} catch (Exception e) {
			try {
				EPClient.saveException(request, e);
			} catch (Exception e1) {
				logger.warn("epclient filter exception message: {}, e:{}", e1.getMessage(), e1);
			}
			throw e;
		}
	}

	private boolean isEpClientRequest(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		int length = epClientPrefix.length();
		String epClientUrlPattern = epClientPrefix.substring(0, length - 1);
		Pattern pattern = Pattern.compile(epClientUrlPattern);
		Matcher matcher = pattern.matcher(requestUri.trim());
		if (requestUri.startsWith(epClientPrefix) || matcher.matches()) {
			return true;
		}
		return false;
	}

	public void dispatch2EPClient(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		servletRequest.getRequestDispatcher("http:localhost:8080/eapa");
	}

	@Override
	public void destroy() {

	}
}
