package org.recipia.alarm.listener

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.awspring.cloud.sqs.annotation.SqsListener
import org.recipia.alarm.dto.SnsNotificationDto
import org.recipia.alarm.dto.message.MemberFollowEvent
import org.recipia.alarm.dto.message.MemberIdDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AwsSqsListener {

    @Value("\${cloud.aws.sqs.member-follow-sqs}")
    private lateinit var memberFollowQueue: String

    @Value("\${cloud.aws.sqs.member-signup-sqs}")
    private lateinit var memberSignupQueue: String

//    @SqsListener("#{sqsListener.memberFollowQueue}")
    @SqsListener("dev-sqs-test")
    fun receiveMemberFollowMessage(message: String) {
        // Jackson ObjectMapper로 JSON 메시지 파싱
        val objectMapper = jacksonObjectMapper()
        val snsNotification: SnsNotificationDto = objectMapper.readValue(message)
        val memberFollowEvent: MemberFollowEvent = objectMapper.readValue(snsNotification.Message)
        val traceId = snsNotification.MessageAttributes.traceId.Value

        // 여기서 memberFollowEvent와 traceId를 사용하여 필요한 로직 처리
        println("Received message with Trace ID: $traceId and Event: $memberFollowEvent")
    }

//    @SqsListener("dev-sqs-test")
    fun receiveMemberSignupMessage(message: String) {
        // Jackson ObjectMapper로 JSON 메시지 파싱
        val objectMapper = jacksonObjectMapper()
        val snsNotification: SnsNotificationDto = objectMapper.readValue(message)
        val memberId: MemberIdDto = objectMapper.readValue(snsNotification.Message)
        val traceId = snsNotification.MessageAttributes.traceId.Value

        // 여기서 memberId와 traceId를 사용하여 필요한 로직 처리
        println("Received message with Trace ID: $traceId and Event: $memberId")

    }

}