package com.ahmetcan7.productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication(
        scanBasePackages = {
                "com.ahmetcan7.productservice",
                "com.ahmetcan7.amqp"
        }
)
@EnableElasticsearchRepositories(basePackages = "com.ahmetcan7.productservice.repository")
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class,args);
    }
}
