package org.recipia.alarm.listener.spring

import org.recipia.alarm.dto.NicknameDto
import org.recipia.alarm.feign.MemberFeignClient
import org.recipia.alarm.springevent.FeignNicknameSpringEvent
import org.recipia.alarm.springevent.MemberSignupSpringEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

/**
 * feign 요청을 담당하는 스프링 이벤트 리스너
 */
@Component
class RequestFeignClient (
        private val memberFeignClient: MemberFeignClient,
        private val eventPublisher: ApplicationEventPublisher
) {

    /**
     * Feign 클라이언트로 Member서버에서 회원가입되어 저장된 닉네임을 요청하는 리스너
     */
    @EventListener
    fun requestSignUpMemberNickname(event: MemberSignupSpringEvent)  {
        val memberId = event.memberId

        val nicknameDto: NicknameDto = memberFeignClient.getNickname(memberId)

        // member 서버에서 응답받은 NicknameDto가 존재할때 DynamoDB 저장을 위한 스프링 이벤트 발행
        val feignNicknameSpringEvent: FeignNicknameSpringEvent = FeignNicknameSpringEvent(nicknameDto)
        eventPublisher.publishEvent(feignNicknameSpringEvent)
    }
}