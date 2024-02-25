package org.recipia.alarm.listener.aws

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.awspring.cloud.sqs.annotation.SqsListener
import org.recipia.alarm.dto.SnsNotificationDto
import org.recipia.alarm.dto.message.MemberFollowEvent
import org.recipia.alarm.dto.message.MemberIdDto
import org.recipia.alarm.common.springevent.MemberSignupSpringEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class AwsSqsListener (
    private val eventPublisher: ApplicationEventPublisher
) {

    @Value("\${cloud.aws.sqs.member-follow-sqs}")
    private lateinit var memberFollowQueue: String

    @Value("\${cloud.aws.sqs.member-signup-sqs}")
    private lateinit var memberSignupQueue: String


//    @SqsListener("#{sqsListener.memberFollowQueue}")
    fun receiveMemberFollowMessage(message: String) {
        // Jackson ObjectMapper로 JSON 메시지 파싱
        val objectMapper = jacksonObjectMapper()
        val snsNotification: SnsNotificationDto = objectMapper.readValue(message)
        val memberFollowEvent: MemberFollowEvent = objectMapper.readValue(snsNotification.Message)
        val traceId = snsNotification.MessageAttributes.traceId.Value

        // 여기서 memberFollowEvent와 traceId를 사용하여 필요한 로직 처리
        println("Received message with Trace ID: $traceId and Event: $memberFollowEvent")
    }

    @SqsListener("#{AwsSqsListener.memberSignupQueue}")
    fun receiveMemberSignupMessage(message: String) {
        // Jackson ObjectMapper로 JSON 메시지 파싱
        val objectMapper = jacksonObjectMapper()
        val snsNotification: SnsNotificationDto = objectMapper.readValue(message)
        val memberIdDto: MemberIdDto = objectMapper.readValue(snsNotification.Message)
        val traceId = snsNotification.MessageAttributes.traceId.Value

        // 회원가입 이벤트 발행
        val memberSignupEvent = MemberSignupSpringEvent(memberIdDto.memberId)
        eventPublisher.publishEvent(memberSignupEvent)

//        println("Received message with Trace ID: $traceId and Event: $memberIdDto.memberId")

    }

}