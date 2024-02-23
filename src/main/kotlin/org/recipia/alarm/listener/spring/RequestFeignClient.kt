package org.recipia.alarm.listener.spring

import jakarta.transaction.Transactional
import org.recipia.alarm.common.springevent.MemberSignupSpringEvent
import org.recipia.alarm.dto.NicknameDto
import org.recipia.alarm.feign.MemberFeignClient
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class RequestFeignClient (
    val memberFeignClient: MemberFeignClient
) {

    /**
     * Feign 클라이언트로 Member서버에서 회원가입되어 저장된 닉네임을 요청하는 리스너
     */
    @Transactional
    @EventListener
    fun requestSignUpMemberNickname(event: MemberSignupSpringEvent) : String {
        val memberId = event.memberId

        val nicknameDto: NicknameDto = memberFeignClient.getNickname(memberId)

        // Feign 요청으로 받은 dto가 존재하면 Nickname 엔티티에서 유저 닉네임 변경
        nicknameDto.let {
            println(nicknameDto.memberId)
            println(nicknameDto.nickname)
        }
        return nicknameDto.nickname
    }
}