package org.recipia.alarm.config

import feign.Logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * feign client 관련 로깅 설정
 * (alarm 프로젝트 로깅 관련 X)
 */
@Configuration
class LoggingConfig {

    @Bean
    fun feignLoggerLevel(): Logger.Level {
        return Logger.Level.FULL
    }

}