import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.2"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
	kotlin("plugin.jpa") version "1.9.22"
}

group = "org.recipia"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2023.0.0"


dependencies {

	// Kotlin 표준 라이브러리
	implementation("org.jetbrains.kotlin:kotlin-stdlib")

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")

	// AWS
	// SQS
	implementation("io.awspring.cloud:spring-cloud-aws-dependencies:3.1.0")
	implementation("io.awspring.cloud:spring-cloud-aws-starter-sqs:3.1.0")
	// DynamoDB
	implementation("aws.sdk.kotlin:dynamodb:1.0.64")


	// Spring Data DynamoDB (선택적, DynamoDB 작업을 용이하게 해줌)
//	implementation("io.github.boostchicken:spring-data-dynamodb:5.2.5")

	// spring cloud openfeign
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

	// OkHttp3
	implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.10")

}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
