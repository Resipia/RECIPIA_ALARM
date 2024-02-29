package org.recipia.alarm.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@EnableFeignClients(basePackages = ["org.recipia.alarm.feign"])
@Configuration
class FeignClientConfig {
}