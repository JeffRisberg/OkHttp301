package com.company.examples;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.StringWriter;

public class PostJsonExample {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    ObjectMapper objectMapper = new ObjectMapper();

    String post(String url) throws IOException {

        JSONObject accessParamMap = new JSONObject();
        accessParamMap.put("buttonElementId", "submit_button");
        accessParamMap.put("colorTheme", "green");
        accessParamMap.put("deployUrl", "http://ncoding.io");
        accessParamMap.put("descriptionElementId", "submit_description");
        accessParamMap.put("formElementId", "submit_form");
        accessParamMap.put("subjectElementId", "submit_subject");
        accessParamMap.put("restServer", "https://api.prod2.aisera.com/restserver");
        accessParamMap.put("successUrl", "http://ncoding.io/index.html");

        JSONObject bodyJson = new JSONObject();
        bodyJson.put("accessParamMap", accessParamMap);
        //bodyJson.put("channelTypeId", channel.getChannelTypeId());
        //bodyJson.put("tenantId", tenantId);
        //bodyJson.put("tenantUserId", channel.getTenantUserId());
        //bodyJson.put("channelName", channel.getChannelName());
        bodyJson.put("appKey", null);
        bodyJson.put("token", null);

        StringWriter stringWriter = new StringWriter();
        bodyJson.writeJSONString(stringWriter);

        System.out.println(stringWriter.toString());
        RequestBody body = RequestBody.create(JSON, stringWriter.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static void main(String[] args) throws IOException {
        PostJsonExample example = new PostJsonExample();

        String response = example.post("http://www.roundsapp.com/post");
        System.out.println(response);
    }
}