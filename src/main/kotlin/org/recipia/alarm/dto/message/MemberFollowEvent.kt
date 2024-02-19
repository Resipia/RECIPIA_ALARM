package org.recipia.alarm.dto.message

data class MemberFollowEvent(
        val followerId: Long,
        val targetMemberId: Long
)
