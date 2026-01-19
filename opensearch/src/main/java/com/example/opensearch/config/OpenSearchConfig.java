package com.example.opensearch.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.ssl.SSLContextBuilder;
import org.opensearch.client.RestClient;
import org.opensearch.client.transport.OpenSearchTransport;
import org.opensearch.client.transport.rest_client.RestClientTransport;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;

@Configuration
public class OpenSearchConfig {

  @Bean
  public OpenSearchClient openSearchClient(
      @Value("${opensearch.host}") String host,
      @Value("${opensearch.port}") int port,
      @Value("${opensearch.username}") String username,
      @Value("${opensearch.password}") String password
  ) throws Exception {

    var creds = new BasicCredentialsProvider();
    creds.setCredentials(AuthScope.ANY,
        new UsernamePasswordCredentials(username, password));

    SSLContext sslContext = SSLContextBuilder.create()
        .loadTrustMaterial(null, (x, y) -> true) // DEV ONLY
        .build();

    var restClient = RestClient.builder(new HttpHost(host, port, "https"))
        .setHttpClientConfigCallback(hc -> hc
            .setDefaultCredentialsProvider(creds)
            .setSSLContext(sslContext)
        )
        .build();

    OpenSearchTransport transport =
        new RestClientTransport(restClient, new JacksonJsonpMapper());

    return new OpenSearchClient(transport);
  }
}
