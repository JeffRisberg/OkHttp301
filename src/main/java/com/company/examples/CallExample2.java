package com.company.examples;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

public class CallExample2 {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .build();

    ObjectMapper objectMapper = new ObjectMapper();
    JSONParser jsonParser = new JSONParser();

    String run(String url) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JSONParser jsonParser = new JSONParser();

        JSONObject accessParamMap = new JSONObject();
        accessParamMap.put("buttonElementId", "submit_button");
        accessParamMap.put("colorTheme", "green");
        accessParamMap.put("deployUrl", "http://ncoding.io");
        accessParamMap.put("descriptionElementId", "submit_description");
        accessParamMap.put("formElementId", "submit_form");
        accessParamMap.put("subjectElementId", "submit_subject");
        accessParamMap.put("restServer", "https://api.prod2.company.com/restserver");
        accessParamMap.put("successUrl", "http://ncoding.io/index.html");

        JSONObject bodyJson = new JSONObject();
        bodyJson.put("accessParamMap", accessParamMap);
        bodyJson.put("appKey", "12345678");
        bodyJson.put("token", "qwertyuiop");

        StringWriter stringWriter = new StringWriter();
        bodyJson.writeJSONString(stringWriter);

        System.out.println(stringWriter.toString());
        RequestBody body = RequestBody.create(JSON, stringWriter.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = client.newCall(request);

        Response response = call.execute();

        try {
            JSONObject responseBodyAsJson = (JSONObject) jsonParser.parse(response.body().string());

            JSONObject accessParamsJsonFromServer = (JSONObject) responseBodyAsJson.get("data");

            String token = accessParamsJsonFromServer.get("token").toString();
            System.out.println("token: " + token);

            String appKey = accessParamsJsonFromServer.get("appKey").toString();
            System.out.println("appKey: " + appKey);

            return "";
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) throws IOException {
        CallExample2 example = new CallExample2();

        String response = example.run("http://postman-echo.com/post");
        System.out.println(response);
    }
}
