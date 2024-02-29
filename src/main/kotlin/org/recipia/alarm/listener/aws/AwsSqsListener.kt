package org.recipia.alarm.listener.aws

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.awspring.cloud.sqs.annotation.SqsListener
import org.recipia.alarm.dto.message.MemberFollowEvent
import org.recipia.alarm.dto.message.MemberIdDto
import org.recipia.alarm.dto.sns.SnsNotificationDto
import org.recipia.alarm.logger
import org.recipia.alarm.springevent.MemberSignupSpringEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

/**
 * AWS SQS 리스너 클래스
 */
@Component
class AwsSqsListener (
    private val eventPublisher: ApplicationEventPublisher
) {

    val log = logger()

    /**
     * 회원가입 SQS 리스너 담당
     */
    @SqsListener("\${cloud.aws.sqs.member-signup-sqs}")
    fun receiveMemberSignupMessage(message: String) {
        // Jackson ObjectMapper로 JSON 메시지 파싱
        val objectMapper = jacksonObjectMapper()
        val snsNotification: SnsNotificationDto = objectMapper.readValue(message)
        val memberIdDto: MemberIdDto = objectMapper.readValue(snsNotification.Message)

        // todo: 아래 traceId로 새로운 trace 생성 후 span 추가
        val traceId = snsNotification.MessageAttributes.traceId.Value

        // 회원가입 이벤트 발행
        val memberSignupEvent = MemberSignupSpringEvent(memberIdDto.memberId)
        eventPublisher.publishEvent(memberSignupEvent)
    }

    /**
     * 멤버 팔로우 SQS 리스너 담당
     */
    @SqsListener("\${cloud.aws.sqs.member-follow-sqs}")
    fun receiveMemberFollowMessage(message: String) {
        // Jackson ObjectMapper로 JSON 메시지 파싱
        val objectMapper = jacksonObjectMapper()
        val snsNotification: SnsNotificationDto = objectMapper.readValue(message)
        val memberFollowEvent: MemberFollowEvent = objectMapper.readValue(snsNotification.Message)

        // todo: 아래 traceId로 새로운 trace 생성 후 span 추가
        val traceId = snsNotification.MessageAttributes.traceId.Value

        // 여기서 memberFollowEvent와 traceId를 사용하여 필요한 로직 처리
        log.info("Received message with Trace ID: $traceId and Event: $memberFollowEvent")
    }


}