package com.example.deptest;

import static org.assertj.core.api.Assertions.assertThat;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppTest {

    private HttpServer server;
    private String base;

    @BeforeEach
    void start() throws IOException {
        server = App.create(0);
        server.start();
        base = "http://localhost:" + server.getAddress().getPort();
    }

    @AfterEach
    void stop() {
        server.stop(0);
    }

    @Test
    void healthReturnsOk() throws Exception {
        HttpResponse<String> r = get("/health");
        assertThat(r.statusCode()).isEqualTo(200);
        assertThat(r.body()).isEqualTo("ok");
    }

    @Test
    void greetUsesNameFromQuery() throws Exception {
        HttpResponse<String> r = get("/greet?name=ana");
        assertThat(r.body()).isEqualTo("Olá, Ana!");
    }

    @Test
    void greetWithoutNameUsesDefault() throws Exception {
        assertThat(get("/greet").body()).isEqualTo("Olá, Mundo!");
    }

    @Test
    void queryParamDecodesValues() {
        assertThat(App.queryParam("name=Jo%C3%A3o&x=1", "name")).isEqualTo("João");
        assertThat(App.queryParam(null, "name")).isNull();
    }

    private HttpResponse<String> get(String path) throws Exception {
        return HttpClient.newHttpClient().send(
                HttpRequest.newBuilder(URI.create(base + path)).build(),
                HttpResponse.BodyHandlers.ofString());
    }
}
