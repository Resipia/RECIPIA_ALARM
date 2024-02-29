package org.recipia.alarm.service

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import aws.sdk.kotlin.services.dynamodb.model.PutItemRequest
import org.recipia.alarm.logger
import org.springframework.stereotype.Service

/**
 * AWS DynamoDB와 연동하는 서비스 클래스
 */
@Service
class DynamoDBService (
        private val dynamoDbClient: DynamoDbClient
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
            if (retryCount < 2) { // 최초 시도 후 실패하면 2번 더 재시도하도록 수정
                log.warn("닉네임 정보 DynamoDB에 저장 재시도 중... (재시도 횟수: ${retryCount + 1})")
                addNicknameToDB(memberId, nickname, retryCount + 1)
            } else {
                log.error("닉네임 정보 DynamoDB에 저장 최종 실패: $memberId, $nickname")
            }
        }
    }
}