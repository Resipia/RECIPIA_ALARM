package org.recipia.alarm.dto

/**
 * feign 요청으로 응답받을 memberId, nickname을 담을 DTO 객체
 */
data class NicknameDto(
        val memberId: Long,
        val nickname: String
)
