package com.activity.tracker.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ElasticSearchConfig {

    @Value("${elasticsearch.host}")
    private String elasticSearchHost;

    @Value("${elasticsearch.port}")
    private int elasticSearchPort;

    @Bean
    @Primary
    public RestHighLevelClient elasticSearchClient() {
        return new RestHighLevelClient(RestClient.builder(new HttpHost(elasticSearchHost, elasticSearchPort)));
    }
}
