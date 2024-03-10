package org.recipia.alarm.springevent

import org.recipia.alarm.dto.NicknameDto

/**
 * feign 요청으로 응답받은 NicknameDto가 존재할때 (feign 요청에 성공했을때)
 * feign 요청 성공했다는 스프링 이벤트
 * (현재는 feign 응답 객체 그대로 필드로 선언
 * 추후 변경될 수 있음)
 */
data class FeignNicknameSpringEvent(
    val nicknameDto: NicknameDto
)
