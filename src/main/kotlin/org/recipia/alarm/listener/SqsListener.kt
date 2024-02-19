package org.recipia.alarm.listener

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.recipia.alarm.dto.SnsNotificationDto
import org.recipia.alarm.dto.message.MemberFollowEvent
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener

class SqsListener {

    @SqsListener("member-follow-sqs")
    fun receiveMessage(message: String) {
        // Jackson ObjectMapper로 JSON 메시지 파싱
        val objectMapper = jacksonObjectMapper()
        val snsNotification: SnsNotificationDto = objectMapper.readValue(message)
        val memberFollowEvent: MemberFollowEvent = objectMapper.readValue(snsNotification.Message)
        val traceId = snsNotification.MessageAttributes.traceId.Value

        // 여기서 memberFollowEvent와 traceId를 사용하여 필요한 로직 처리
        println("Received message with Trace ID: $traceId and Event: $memberFollowEvent")
    }


}