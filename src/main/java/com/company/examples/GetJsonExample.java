package com.company.examples;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.bind.v2.model.core.TypeRef;
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
        List<SimplePost> posts = objectMapper.readValue(response, new TypeRef<List<SimplePost>>(){});
    }

    class SimplePost {
        int id;
        int userId;
        String title;
        String body;

        public SimplePost() {
        }

        public SimplePost(int id, int userId, String title, String body) {
            this.id = id;
            this.userId = userId;
            this.title = title;
            this.body = body;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }
}