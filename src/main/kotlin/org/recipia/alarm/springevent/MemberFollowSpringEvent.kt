package org.recipia.alarm.springevent

import org.recipia.alarm.dto.message.MemberFollowMessage

/**
 * 멤버 팔로우 SQS 리스너가 활성화됐을때 발행되는 스프링 이벤트
 * (현재는 SNS Message에 담겨있는 객체 그대로 필드로 선언
 * 추후 변경될 수 있음)
 */
data class MemberFollowSpringEvent (
    val followMessage: MemberFollowMessage
)