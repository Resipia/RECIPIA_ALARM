package org.recipia.alarm.listener.spring

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.recipia.alarm.service.DynamoDBService
import org.recipia.alarm.springevent.FeignNicknameSpringEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class DynamoDBEventListener (
        private val dynamoDBService: DynamoDBService
) : CoroutineScope by CoroutineScope(Dispatchers.IO) {

    /**
     * 멤버 서버로 nickname을 요청하는 feign이 성공했을때 dynamoDB에 해당 데이터를 저장하는 이벤트 리스너
     */
    @EventListener
    fun insertNicknameToDynamoDB (event: FeignNicknameSpringEvent) {
        launch {
            dynamoDBService.addNicknameToDB(event.nicknameDto.memberId, event.nicknameDto.nickname)
        }
    }

}