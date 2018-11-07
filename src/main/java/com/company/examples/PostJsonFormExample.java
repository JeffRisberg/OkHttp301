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
                .add("tenantId", "10000")
                .add("dataSourceId", "1")
                .add("type", "SalesForce")
                .add("parameters", "{\"alpha\": 56, \"beta\": \"elephant\"}")
                .add("data", "{\"gamma\": 123, \"delta\": \"zebra\"}")
                .build();
        Request request = new Request.Builder()
                .post(formBody)
                .url(url)
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
        PostJsonFormExample example = new PostJsonFormExample();

        String response = example.post("http://localhost:32750/api/v1/incidents/incident");
        System.out.println(response);
    }
}