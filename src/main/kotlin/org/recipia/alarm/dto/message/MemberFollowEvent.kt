package org.recipia.alarm.dto.message

/**
 * 멤버 팔로우 SQS 리스너가 활성화됐을때 발행되는 멤버 팔로우 스프링 이벤트
 */
data class MemberFollowEvent(
        val followerId: Long,       // 팔로우를 건 memberId
        val targetMemberId: Long    // 팔로우를 당한 memberId
)
