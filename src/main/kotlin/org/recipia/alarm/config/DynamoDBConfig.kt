package org.recipia.alarm.config

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DynamoDBConfig {

    @Bean
    fun dynamoDbClient(): DynamoDbClient {
        return DynamoDbClient { region = "ap-northeast-2" } // AWS 리전 설정
    }

}