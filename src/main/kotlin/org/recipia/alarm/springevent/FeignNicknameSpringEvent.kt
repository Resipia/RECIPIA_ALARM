package org.recipia.alarm.springevent

import org.recipia.alarm.dto.NicknameDto

/**
 * feign 요청으로 응답받은 NicknameDto가 존재할때 (feign 요청에 성공했을때)
 * feign 요청 성공했다는 스프링 이벤트
 * (여기에 담긴 NicknameDto로 DynamoDB 데이터 저장 진행)
 */
data class FeignNicknameSpringEvent(
        val nicknameDto: NicknameDto
)
