package com.company.examples;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class GetWithHeadersExample {
    OkHttpClient client = new OkHttpClient();

    String run(String baseUrl, String queryParam) throws IOException {
        // Build URL with query parameters
        HttpUrl url = HttpUrl.parse(baseUrl).newBuilder()
                .addQueryParameter("q", queryParam)
                .addQueryParameter("format", "json")
                .build();

        // Build request with custom headers
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "OkHttp301-Example/1.0")
                .header("Accept", "application/json")
                .header("Accept-Language", "en-US,en;q=0.9")
                .build();

        try (Response response = client.newCall(request).execute()) {
            // Check response status code
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            // Print response headers
            System.out.println("Response Code: " + response.code());
            System.out.println("Response Message: " + response.message());
            System.out.println("Content-Type: " + response.header("Content-Type"));
            
            return response.body().string();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Running GetWithHeadersExample");
        GetWithHeadersExample example = new GetWithHeadersExample();
        
        // Example: Search API with query parameters and custom headers
        String response = example.run("https://httpbin.org/get", "test");
        System.out.println("\nResponse Body:");
        System.out.println(response);
    }
}

