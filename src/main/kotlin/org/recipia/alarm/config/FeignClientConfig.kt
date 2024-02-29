package org.recipia.alarm.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

/**
 * feign client 설정 파일
 */
@EnableFeignClients(basePackages = ["org.recipia.alarm.feign"])
@Configuration
class FeignClientConfig {
}