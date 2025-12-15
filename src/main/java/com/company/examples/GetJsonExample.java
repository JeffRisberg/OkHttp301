package com.company.examples;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GetJsonExample {
    private static final int MAX_RESPONSE_SIZE = 10 * 1024 * 1024; // 10MB
    
    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build();

    private void validateUrl(String urlString) throws IllegalArgumentException {
        try {
            URL url = new URL(urlString);
            String protocol = url.getProtocol().toLowerCase();
            if (!protocol.equals("http") && !protocol.equals("https")) {
                throw new IllegalArgumentException("Only HTTP and HTTPS protocols are allowed");
            }
            if (!protocol.equals("https")) {
                System.out.println("Warning: Using non-secure HTTP protocol");
            }
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL: " + e.getMessage(), e);
        }
    }

    String run(String url) throws IOException {
        validateUrl(url);
        
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Request failed with code: " + response.code());
            }
            
            if (response.body() == null) {
                throw new IOException("Response body is null");
            }
            
            // Limit response size to prevent memory exhaustion
            long contentLength = response.body().contentLength();
            if (contentLength > MAX_RESPONSE_SIZE) {
                throw new IOException("Response too large: " + contentLength + " bytes");
            }
            
            String responseBody = response.body().string();
            if (responseBody.length() > MAX_RESPONSE_SIZE) {
                throw new IOException("Response body exceeds maximum size");
            }
            
            return responseBody;
        }
    }

    public static void main(String[] args) {
        // Configure ObjectMapper with security safeguards
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY, true);

        GetJsonExample example = new GetJsonExample();
        
        try {
            String response = example.run("https://jsonplaceholder.typicode.com/posts");
            System.out.println("Response received (first 200 chars): " + 
                    response.substring(0, Math.min(200, response.length())) + "...");
            
            List<SimplePostExample> posts = objectMapper.readValue(response, new TypeReference<List<SimplePostExample>>() {
            });
            
            System.out.println("Successfully parsed " + posts.size() + " posts");
            if (!posts.isEmpty()) {
                System.out.println("First post: " + posts.get(0));
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid URL: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Request failed: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}