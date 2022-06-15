package com.tushaar.miniassignment.configuration;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

@Configuration
@PropertySource("classpath:application.properties")
@EnableDynamoDBRepositories
(basePackages = "com.tushaar.miniassignment.repository")
public class DynamoDBConfig {
	//@Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint = "http://localhost:8000";

    //@Value("${amazon.aws.accesskey}")
    private String amazonAWSAccessKey = "key1";

    //@Value("${amazon.aws.secretkey}")
    private String amazonAWSSecretKey = "key2";
    
    @Bean
    public DynamoDBMapper dynamoDBMapper() {
    	return new DynamoDBMapper(amazonDynamoDB());
    }
    
    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDB amazonDynamoDB
            = AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, "us-east-1"))
            .withCredentials(awsCredentialsProvider()).build();
        return amazonDynamoDB;
    }
    
//    @Bean
//    private AmazonDynamoDB amazonDynamoDB() {
//        AmazonDynamoDB amazonDynamoDB
//            = AmazonDynamoDBClientBuilder.standard()
//            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, "us-east-1"))
//            .withCredentials(awsCredentialsProvider()).build();
//        return amazonDynamoDB;
//    }
    
    @Bean
    public AWSCredentialsProvider awsCredentialsProvider() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey));
    }

//    @Bean
//    public AmazonDynamoDB amazonDynamoDB() {
//        AmazonDynamoDB amazonDynamoDB 
//          = new AmazonDynamoDBClient(amazonAWSCredentials());
//        
//        if (!StringUtils.isEmpty(amazonDynamoDBEndpoint)) {
//            amazonDynamoDB.setEndpoint(amazonDynamoDBEndpoint);
//        }
//        
//        return amazonDynamoDB;
//    }
//
//    @Bean
//    public AWSCredentials amazonAWSCredentials() {
//        return new BasicAWSCredentials(
//          amazonAWSAccessKey, amazonAWSSecretKey);
//    }
}
