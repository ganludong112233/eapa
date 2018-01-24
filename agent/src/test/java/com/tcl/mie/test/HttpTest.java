package com.tcl.mie.test;

import com.ep.transport.HttpClientUtil;

public class HttpTest {

    public static void main(String[] args) {
        String content =
                "[{\"avgCostTime\":0,\"callCount\":17,\"className\":\"com.tcl.mie.vo.response.ResponseBase\",\"endTime\":1482375360000,\"env\":\"DEV\",\"localIp\":\"10.115.101.129\",\"maxCostTime\":0,\"methodName\":\"getCode\",\"minCostTime\":0,\"projectId\":10001,\"signature\":\"()Ljava/lang/Integer;\",\"startTime\":1482375300000},{\"avgCostTime\":0,\"callCount\":17,\"className\":\"com.tcl.mie.vo.response.ResponseBase\",\"endTime\":1482375360000,\"env\":\"DEV\",\"localIp\":\"10.115.101.129\",\"maxCostTime\":1,\"methodName\":\"getMsg\",\"minCostTime\":0,\"projectId\":10001,\"signature\":\"()Ljava/lang/String;\",\"startTime\":1482375300000}]";
        System.out.println(content);

        HttpClientUtil.postJson("http://localhost:8080/admin/api/perf/stats", content);
    }

}
