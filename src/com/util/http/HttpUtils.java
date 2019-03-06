package com.util.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Auth wn
 * @Date 2019/2/27
 */
public class HttpUtils {


    public static String postParmas(String url, Map<String,String> params){
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        String reqString = appendParams(params);
        StringEntity stringEntity = new StringEntity(reqString, Charset.forName("UTF-8"));
        stringEntity.setContentType("application/x-www-form-urlencoded");
        post.setEntity(stringEntity);
        HttpResponse httpResponse = null;
        try {
            httpResponse =  httpClient.execute(post);
            return EntityUtils.toString(httpResponse.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static String get(String url){
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        HttpResponse httpResponse = null;
        try {
            httpResponse =  httpClient.execute(get);
            return EntityUtils.toString(httpResponse.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static  String appendParams(Map<String, String> reqMap) {
        StringBuffer urlSb = new StringBuffer("");
        Set<String> keys = reqMap.keySet();
        boolean isFirst = true;
        for (String key : keys) {
            if (isFirst) {
                isFirst = false;
                urlSb.append(key + "=" + reqMap.get(key));
            } else {
                urlSb.append("&" + key + "=" + reqMap.get(key));
            }
        }
        return urlSb.toString();
    }


    public static void main(String[] args) {
        Map<String,String > params = new HashMap<>();
        params.put("username","王五");
        System.out.println(get("http://192.168.56.1:8010/user/feign/1"));
    }
}
