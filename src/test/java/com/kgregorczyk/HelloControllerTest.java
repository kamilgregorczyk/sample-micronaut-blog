package com.kgregorczyk;

import static com.google.common.truth.Truth.assertThat;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class HelloControllerTest {

  private static EmbeddedServer server;
  private static HttpClient client;

  @BeforeAll
  public static void setupServer() {
    server = ApplicationContext.run(EmbeddedServer.class);
    client = server.getApplicationContext().createBean(HttpClient.class, server.getURL());
  }

  @AfterAll
  public static void stopServer() {
    if (server != null) {
      server.stop();
    }
    if (client != null) {
      client.stop();
    }
  }

  @Test
  public void testHello() {
    // given
    HttpRequest request = HttpRequest.GET("/");

    // when
    String body = client.toBlocking().retrieve(request);

    // then
    assertThat(body).isEqualTo("Hello World");
  }
}
