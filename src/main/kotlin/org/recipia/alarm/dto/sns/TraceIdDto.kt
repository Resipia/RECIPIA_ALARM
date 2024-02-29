package org.recipia.alarm.dto.sns

/**
 * SNS MessageAttributes에 담겨있는 traceId를 담기위한 DTO
 */
data class TraceIdDto (
    val Type: String,
    val Value: String
)