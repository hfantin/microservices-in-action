import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.1.6.RELEASE"
	id("io.spring.dependency-management") version "1.0.7.RELEASE"
	kotlin("jvm") version "1.3.41"
	kotlin("plugin.spring") version "1.3.41"
}

group = "com.github.hfantin"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "Greenwich.SR2"

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	// dependencies to work with java 9+
	implementation("javax.xml.bind:jaxb-api:2.3.0") 	// 2.3.0
	implementation("com.sun.xml.bind:jaxb-impl:2.3.0") //2.3.0
	implementation("org.glassfish.jaxb:jaxb-runtime:2.3.0") //2.3.0
	implementation("javax.activation:activation:1.1.1") //1.1.1
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}
