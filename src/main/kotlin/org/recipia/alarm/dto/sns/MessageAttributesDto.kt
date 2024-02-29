package org.recipia.alarm.dto.sns

/**
 * SNS messageAttribute에 있는 traceId를 담기 위한 dto
 */
data class MessageAttributesDto (
    val traceId: TraceIdDto
)