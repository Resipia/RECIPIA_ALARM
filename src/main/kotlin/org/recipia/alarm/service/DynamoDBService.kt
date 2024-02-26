package org.recipia.alarm.service

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import aws.sdk.kotlin.services.dynamodb.model.PutItemRequest
import org.recipia.alarm.logger
import org.springframework.stereotype.Service

@Service
class DynamoDBService (
        val dynamoDbClient: DynamoDbClient
) {

    val log = logger()

    /**
     * DynamoDB에 닉네임 정보를 추가하는 메소드
     */
    suspend fun addNicknameToDB(memberId: Long, nickname: String, retryCount: Int = 0) {
        val itemValues = mapOf(
                "memberId" to AttributeValue.N(memberId.toString()),
                "nickname" to AttributeValue.S(nickname)
        )

        val putItemRequest = PutItemRequest {
            tableName = "member_nickname"
            item = itemValues
        }

        try {
            dynamoDbClient.putItem(putItemRequest)
            log.info("닉네임 정보 DynamoDB에 추가 성공: $memberId, $nickname")
        } catch (e: Exception) {
            log.warn("DynamoDB에 닉네임 정보 추가 실패: ${e.message}")
            if (retryCount < 1) { // 재시도 횟수가 1번 미만일 경우 재시도
                log.warn("닉네임 정보 DynamoDB에 저장 재시도 중...")
                addNicknameToDB(memberId, nickname, retryCount + 1)
            } else {
                log.error("닉네임 정보 DynamoDB에 저장 최종 실패: $memberId, $nickname")
            }
        }
    }

}