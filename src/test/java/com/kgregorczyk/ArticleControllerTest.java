package com.kgregorczyk;

import com.kgregorczyk.model.Article;
import io.micronaut.context.ApplicationContext;
import io.micronaut.http.client.HttpClient;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

public class ArticleControllerTest {

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
    var article1 = new Article();
    var article2 = new Article();

    article1.setTitle("1");
    article1.setDescription("1");

    article2.setTitle("2");
    var modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    modelMapper.map(article2, article1);

    assert article1.getTitle().equals("2");
    assert article1.getDescription().equals("1");
  }
}
