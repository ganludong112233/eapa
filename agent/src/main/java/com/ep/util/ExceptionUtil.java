package com.ep.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.ep.inst.trace.Tracer;

public class ExceptionUtil {
	public static ThreadLocal<String> exceptionMarks = new ThreadLocal<String>();
	/**
	 * Save traceId when current thread happened exception.
	 */
	public static void setExecptionMark(){
		exceptionMarks.set(Tracer.getTraceId());
	}
	
	/**
	 * Verify whether if current thread happened exception or not.
	 * @return
	 */
	public static boolean isHappenedExecption(){
		String currentTraceId = Tracer.getTraceId();
		String traceId = exceptionMarks.get();
		if(StringUtil.isBlank(traceId)){
			return false;
		}
		if(traceId.equals(currentTraceId)){
			return true;
		}
		return false;
	}
	
	/**
	 * get the root case
	 * 
	 * @param throwable
	 * @return root cause
	 */
	public static Throwable getRootCause(Throwable throwable) {
		Throwable rootCause = throwable;
		while (rootCause.getCause() != null) {
			rootCause = rootCause.getCause();
		}
		return rootCause;
	}

	/**
	 * get the full exception trace message;
	 * 
	 * @param throwable
	 * @return the full trace msg
	 */
	public static String getFullExceptionTraceMsg(Throwable throwable) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		throwable.printStackTrace(new PrintStream(outputStream));
		String traceMsg = new String(outputStream.toByteArray());
		traceMsg = traceMsg.replace("\n","<br>&nbsp;&nbsp;&nbsp;&nbsp;");
		return traceMsg;
	}
}
