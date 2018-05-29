package com.company.examples;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

public class GetJsonExample {
    OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        GetJsonExample example = new GetJsonExample();
        String response = example.run("https://jsonplaceholder.typicode.com/posts");
        System.out.println(response);
        List<SimplePost> posts = objectMapper.readValue(response, new TypeReference<List<SimplePost>>() {
        });
        System.out.println(posts);
        System.out.println("size:" + posts.size());
    }
}