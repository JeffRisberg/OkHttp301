package com.company.examples;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.fail;

public class ExampleTest {

    static OkHttpClient client;

    @BeforeClass
    public static void setup() {
        client = new OkHttpClient();
    }

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    @Test
    public void test() {
        try {
            String response = run("https://raw.github.com/square/okhttp/master/README.md");
            System.out.println(response);
        } catch (IOException ioe) {
            fail();
        }
    }
}
