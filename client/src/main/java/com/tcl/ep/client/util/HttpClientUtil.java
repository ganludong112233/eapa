package com.tcl.ep.client.util;

import java.io.IOException;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {
    private final static Logger LOG = LoggerFactory.getLogger(HttpClientUtil.class);

    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    public static String postJson(String url, String json) throws IOException {

        HttpPost httpPost = new HttpPost(url);

        httpPost.setEntity(new StringEntity(json, Consts.UTF_8));
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = null;
        String result = null;

        LOG.info("postJson, url: {}, json: {}", url, json);
        try {
            response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
                LOG.info("postJson finished, return: \n{}", result);
            }
        } catch (IOException e) {
            LOG.error("postJson error, url: {}, json: {}", url, json);
            throw e;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }

        return result;
    }
}
