package org.recipia.alarm.dto.sns


/**
 * SNS 메시지 객체를 담을 DTO 객체
 */
data class SnsNotificationDto (
        val Type: String,
        val MessageId: String,
        val TopicArn: String,
        val Message: String,
        val Timestamp: String,
        val SignatureVersion: String,
        val Signature: String,
        val SigningCertURL: String,
        val UnsubscribeURL: String,
        val MessageAttributes: MessageAttributesDto
)