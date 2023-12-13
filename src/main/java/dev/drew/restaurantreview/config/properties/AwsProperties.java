package dev.drew.restaurantreview.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "aws")
public class AwsProperties {

        private String bucketName;
        public String getBucketName() {
                return bucketName;
        }
        public void setBucketName(String bucketName) {
                this.bucketName = bucketName;
        }


}
