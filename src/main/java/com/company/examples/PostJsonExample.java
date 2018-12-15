package com.company.examples;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.StringWriter;

public class PostJsonExample {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    ObjectMapper objectMapper = new ObjectMapper();
    JSONParser jsonParser = new JSONParser();

    String post(String url) throws IOException {

        FormBody.Builder builder = new FormBody.Builder();

        builder.add("tenantId", "10000");
        builder.add("data", "");

        RequestBody formBody = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            JSONObject responseBodyAsJson = (JSONObject) jsonParser.parse(response.body().string());

            return responseBodyAsJson.toJSONString();
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) throws IOException {
        PostJsonExample example = new PostJsonExample();

        String response = example.post("http://postman-echo.com/post");
        System.out.println(response);
    }
}