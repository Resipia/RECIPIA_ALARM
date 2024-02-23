package org.recipia.alarm.feign

import org.recipia.alarm.dto.NicknameDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

@Component
@FeignClient(name = "member-service", url = "\${feign.member_url}")
interface MemberFeignClient {

    /**
     * 멤버 서버로부터 Id로 해당하는 Nickname 값을 받아온다.
     */
    @RequestMapping(method = [RequestMethod.POST], value = ["/feign/member/getNickname"])
    fun getNickname(@RequestParam("memberId") memberId: Long): NicknameDto

}