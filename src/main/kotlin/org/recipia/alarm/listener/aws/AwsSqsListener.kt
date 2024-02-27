package org.recipia.alarm.listener.aws

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.awspring.cloud.sqs.annotation.SqsListener
import org.recipia.alarm.dto.SnsNotificationDto
import org.recipia.alarm.dto.message.MemberFollowEvent
import org.recipia.alarm.dto.message.MemberIdDto
import org.recipia.alarm.logger
import org.recipia.alarm.springevent.MemberSignupSpringEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class AwsSqsListener (
    private val eventPublisher: ApplicationEventPublisher
) {

    val log = logger()

//    @SqsListener("\${cloud.aws.sqs.member-follow-sqs}")
    fun receiveMemberFollowMessage(message: String) {
        // Jackson ObjectMapper로 JSON 메시지 파싱
        val objectMapper = jacksonObjectMapper()
        val snsNotification: SnsNotificationDto = objectMapper.readValue(message)
        val memberFollowEvent: MemberFollowEvent = objectMapper.readValue(snsNotification.Message)
        val traceId = snsNotification.MessageAttributes.traceId.Value

        // 여기서 memberFollowEvent와 traceId를 사용하여 필요한 로직 처리
        log.info("Received message with Trace ID: $traceId and Event: $memberFollowEvent")
    }

    @SqsListener("\${cloud.aws.sqs.member-signup-sqs}")
    fun receiveMemberSignupMessage(message: String) {
        // Jackson ObjectMapper로 JSON 메시지 파싱
        val objectMapper = jacksonObjectMapper()
        val snsNotification: SnsNotificationDto = objectMapper.readValue(message)
        val memberIdDto: MemberIdDto = objectMapper.readValue(snsNotification.Message)
        val traceId = snsNotification.MessageAttributes.traceId.Value

        // 회원가입 이벤트 발행
        val memberSignupEvent = MemberSignupSpringEvent(memberIdDto.memberId)
        eventPublisher.publishEvent(memberSignupEvent)
    }

}