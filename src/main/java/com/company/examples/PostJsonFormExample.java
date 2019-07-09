package com.company.examples;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class PostJsonFormExample {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static final MediaType FORM = MediaType.parse("application/form-data");

    OkHttpClient client = new OkHttpClient();

    ObjectMapper objectMapper = new ObjectMapper();
    JSONParser jsonParser = new JSONParser();

    String post(String url) throws IOException {

        RequestBody formBody = new FormBody.Builder()
                .add("login", "jeff@aisera.com")
                .add("password", "jeff")
                .build();
        Request request = new Request.Builder()
                .post(formBody)
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String bodyStr = response.body().string();
            JSONObject responseBodyAsJson = (JSONObject) jsonParser.parse(bodyStr);

            return responseBodyAsJson.toJSONString();
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) throws IOException {
        PostJsonFormExample example = new PostJsonFormExample();

        String response = example.post("http://localhost:1234/connector-server/v1/tenants/10000/login");
        System.out.println(response);
    }
}