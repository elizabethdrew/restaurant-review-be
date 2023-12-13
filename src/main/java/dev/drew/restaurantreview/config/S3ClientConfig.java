package dev.drew.restaurantreview.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
public class S3ClientConfig {

    @Bean
    private static S3Client s3Client() {
        return S3Client.builder()
                .endpointOverride(URI.create("http://s3.localhost.localstack.cloud:4566"))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("test", "test")))
                .region(Region.US_EAST_1)
                .build();
    }
}
