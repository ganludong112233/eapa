package com.tcl.ep.common.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

public class CommonExceptionResolver extends DefaultHandlerExceptionResolver {

	private static final Logger log = LoggerFactory.getLogger(CommonExceptionResolver.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

		try {
			if (ex instanceof ParamException ||ex instanceof IllegalArgumentException || ex instanceof ServletRequestBindingException || ex instanceof TypeMismatchException
					|| ex instanceof HttpMessageNotReadableException || ex instanceof MethodArgumentNotValidException
					|| ex instanceof MissingServletRequestPartException || ex instanceof BindException) {
				return as400Error(ex, request, response, handler);
			} else if (ex instanceof ServiceException) {
				return asServiceError((ServiceException) ex, request, response, handler);
			} else {
				return as500Error(ex, request, response, handler);
			}
		} catch (Exception handlerException) {
			log.warn("Handling of [" + ex.getClass().getName() + "] resulted in Exception", handlerException);
		}

		return null;
	}

	protected ModelAndView as400Error(Exception ex, HttpServletRequest request, HttpServletResponse response, Object handler)
			throws IOException {

		String retStr = String.format("{\"code\":400,\"msg\":\"%s\"}", ex.getMessage());
		log.warn(retStr);

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().print(retStr);

		return new ModelAndView();
	}

	protected ModelAndView asServiceError(ServiceException ex, HttpServletRequest request, HttpServletResponse response, Object handler)
			throws IOException {

		String retStr = String.format("{\"code\":%d,\"msg\":\"%s\"}", ex.getCode(), ex.getMessage());
		log.warn(retStr, ex);

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().print(retStr);

		return new ModelAndView();
	}

	protected ModelAndView as500Error(Exception ex, HttpServletRequest request, HttpServletResponse response, Object handler)
			throws IOException {

		String retStr = String.format("{\"code\":500,\"msg\":\"%s\"}", ex.getMessage());
		log.error(retStr, ex);

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().print(retStr);

		return new ModelAndView();
	}

}
