package com.tcl.ep.admin.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 登录验证信息
 */
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String path = httpServletRequest.getServletPath();

        if (!path.startsWith("/admin")) {
            // 如果不是以admin开头的，则不处理
            chain.doFilter(request, response);
        } else {

            // 检查是否已经登录
            Object userInfo = httpServletRequest.getSession().getAttribute("userInfo");

            if (userInfo == null) {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login");
            } else {
                // 请求成功，这个应该继续
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
