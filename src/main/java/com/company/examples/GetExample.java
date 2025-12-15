package com.company.examples;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class GetExample {
    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(Duration.ofSeconds(10))
            .readTimeout(Duration.ofSeconds(10))
            .writeTimeout(Duration.ofSeconds(10))
            .build();

    String run(String url) throws IOException {
        // Validate URL scheme to mitigate SSRF via non-http(s) protocols
        try {
            URI uri = new URI(url);
            String scheme = uri.getScheme();
            if (scheme == null || !(scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https"))) {
                throw new IOException("Unsupported URL scheme: " + scheme);
            }
        } catch (URISyntaxException e) {
            throw new IOException("Invalid URL: " + url, e);
        }

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected HTTP code: " + response.code());
            }
            if (response.body() == null) {
                throw new IOException("Empty response body");
            }

            // Limit response size to avoid excessive memory usage
            final long maxBytes = 2 * 1024 * 1024; // 2 MB cap for example
            Long contentLength = response.body().contentLength();
            if (contentLength != -1 && contentLength > maxBytes) {
                throw new IOException("Response too large: " + contentLength + " bytes");
            }

            String body = response.body().string();
            if (body.length() > maxBytes) {
                throw new IOException("Response string too large: " + body.length() + " chars");
            }
            return body;
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Running GetExample");
        GetExample example = new GetExample();
        String response = example.run("https://httpbin.org/get");
        System.out.println(response);
    }
}
