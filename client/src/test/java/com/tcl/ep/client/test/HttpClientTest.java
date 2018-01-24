package com.tcl.ep.client.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import com.tcl.ep.client.EPClient;
import com.tcl.ep.client.util.HttpClientUtil;

public class HttpClientTest {
    /*
     * @Test public void test() throws IOException { ExceptionInfo info=new ExceptionInfo();
     * info.setProjectId(10001L); info.setExceptionName("ERROR"); info.setModule("admin");
     * info.setErrorMsg("dshkasdhvadsbvjks"); info.setOccurTime(new Date().getTime());
     * info.setHandlerClass("contentService"); info.setRequestMethod("search");
     * System.out.println("---------"+JSONObject.toJSONString(info));
     * HttpClientUtil.postJson("http://localhost:8080/admin/exception/add",
     * JSONObject.toJSONString(info)); }
     */
    @SuppressWarnings("resource")
	@Test
    public void testExceptionInfo() throws IOException {
        File file = new File("/data/test.txt");
        String filepath = file.getAbsolutePath();
        System.out.println(filepath);
        /*try {
            @SuppressWarnings("unused")
			BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            EPClient.saveException(e);
            e.printStackTrace();
        }*/
        HttpClientUtil.postJson("www.baidu.com", null);
    }

}
