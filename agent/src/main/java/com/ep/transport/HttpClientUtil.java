package com.ep.transport;

import java.io.IOException;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    public static String postJson(String url, String json) {

        HttpPost httpPost = new HttpPost(url);

        httpPost.setEntity(new StringEntity(json, Consts.UTF_8));
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = null;
        String result = null;

        try {
            response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }
}
