package com.ep.config;

import com.ep.util.PropertiesParser;

public class AbstractConfig {
	protected final static PropertiesParser properties = Configuration
			.getPropParser();
	private static String FILE_BUFFER_OUT_SIZE = "fileout.char.buffersize";

	public static int getFileOutBufferSize() {
		int defaultSize = 8192;
		int byteSize = properties.getInt(FILE_BUFFER_OUT_SIZE, defaultSize);
		if (byteSize <= 0) {
			byteSize = defaultSize;
		}
		return byteSize;
	}
}
