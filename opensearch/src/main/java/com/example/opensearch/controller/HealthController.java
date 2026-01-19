package com.example.opensearch.controller;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

  private final OpenSearchClient client;

  public HealthController(OpenSearchClient client) {
    this.client = client;
  }

  @GetMapping("/health/opensearch")
  public String health() throws Exception {
    var info = client.info();
    return "Connected to OpenSearch " + info.version().number();
  }
}
