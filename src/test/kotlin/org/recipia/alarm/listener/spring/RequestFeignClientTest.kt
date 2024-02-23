package org.recipia.alarm.listener.spring

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.recipia.alarm.common.springevent.MemberSignupSpringEvent
import org.recipia.alarm.dto.NicknameDto
import org.recipia.alarm.feign.MemberFeignClient

@DisplayName("[단위] 멤버 서버로 feign 요청 보내는 서비스 클래스 테스트")
class RequestFeignClientTest {

    @Mock
    lateinit var sut: MemberFeignClient     // lateinit을 사용하여 non-nullable 타입으로 선언

    @InjectMocks
    lateinit var requestFeignClient: RequestFeignClient

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }


    @DisplayName("멤버 서비스로부터 닉네임을 성공적으로 받아오는지 확인")
    @Test
    fun requestSignUpMemberNicknameTest() {
        // Given
        val memberId = 1L
        val nicknameDto = NicknameDto(1L, "TestNickname")
        given(sut.getNickname(memberId)).willReturn(nicknameDto)

        // When
        val nickname = requestFeignClient.requestSignUpMemberNickname(MemberSignupSpringEvent(memberId))

        // Then
        verify(sut).getNickname(memberId)
        assert(nickname.equals(nicknameDto.nickname))
    }
}