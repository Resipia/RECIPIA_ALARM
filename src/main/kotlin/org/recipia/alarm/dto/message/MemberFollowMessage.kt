package org.recipia.alarm.dto.message

/**
 * 멤버 팔로우 SNS message에 담겨있는 member follow dto
 */
data class MemberFollowMessage(
        val followerId: Long,       // 팔로우를 건 memberId
        val targetMemberId: Long    // 팔로우를 당한 memberId
)
