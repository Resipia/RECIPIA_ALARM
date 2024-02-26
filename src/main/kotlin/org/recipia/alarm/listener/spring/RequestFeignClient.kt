package org.recipia.alarm.listener.spring

import jakarta.transaction.Transactional
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.recipia.alarm.common.springevent.MemberSignupSpringEvent
import org.recipia.alarm.dto.NicknameDto
import org.recipia.alarm.feign.MemberFeignClient
import org.recipia.alarm.service.DynamoDBService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class RequestFeignClient (
    val memberFeignClient: MemberFeignClient,
    val dynamoDBService: DynamoDBService

) {

    /**
     * Feign 클라이언트로 Member서버에서 회원가입되어 저장된 닉네임을 요청하는 리스너
     */
    @Transactional
    @EventListener
    fun requestSignUpMemberNickname(event: MemberSignupSpringEvent)  {
        val memberId = event.memberId

        val nicknameDto: NicknameDto = memberFeignClient.getNickname(memberId)

        // DynamoDBService를 사용하여 DynamoDB에 데이터 추가
        GlobalScope.launch {
            dynamoDBService.addNicknameToDB(nicknameDto.memberId, nicknameDto.nickname)
        }

    }
}