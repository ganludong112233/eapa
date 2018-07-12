/**
 * @Title: Mainafd.java
 * @Package com.tcl.mie.test
 * @Description: TODO
 * @author liuyi
 * @date 2018年6月29日
 * @version V1.0
 */
package com.tcl.mie.test;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @ import java.net.URL; ClassName: Mainafd
 * 
 * @Description: TODO
 * @author liuyi
 * @date 2018年6月29日
 * 
 */
public class URLDEMO {

    /**
     * @throws URISyntaxException
     * @throws MalformedURLException
     * @Title: main
     * @Description: TODO
     * @param @param args
     * @return void
     * @throws
     */
    public static void main(String[] args) throws MalformedURLException, URISyntaxException {
        URL url =
                new URL(
                        "jar:file:/data/develop/workspace/java/springcloud/springcloud-demo-service/target/eureka-client.jar!/BOOT-INF/lib/aspectjweaver-1.8.10.jar!/");
        System.out.println(url.getPath());
        System.out.println(url.toString());
        System.out.println(url.toURI().getPath());
        System.out.println(url.toURI().getScheme());
        System.out.println(url.toURI().getRawSchemeSpecificPart());
        System.out.println(url.toExternalForm());
        System.out.println(url.toString());

    }

}
