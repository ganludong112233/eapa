package com.tcl.ep.client.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ExceptionUtil {
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
