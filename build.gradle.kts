plugins {
    java
    // Bump Kotlin to 1.9.24+ or 2.0+
    id("org.jetbrains.kotlin.jvm") version "2.0.0"

    // Bump Spring Boot & Dependency Management
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.bartergrid"
version = "0.0.1-SNAPSHOT"
description = "hedera microservice"

java {
    java.sourceCompatibility = JavaVersion.VERSION_17
    java.targetCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
    maven {
        url = uri("https://u-node6:8997/repository/maven-public/")
        credentials {
            username = project.findProperty("nexusUsername") as String? ?: System.getenv("NEXUS_USERNAME")
            password = project.findProperty("nexusPassword") as String? ?: System.getenv("NEXUS_PASSWORD")
        }
        // Disable SSL verification NON-PRODUCTION ONLY!
        isAllowInsecureProtocol = true
    }
}

extra["springCloudVersion"] = "2023.0.1"

dependencies {
    // 1. Force all OTel components to a version that includes LoggerProvider
    implementation(platform("io.opentelemetry:opentelemetry-bom:1.31.0"))
    // 2. Micrometer Bridge - Required for Spring Boot 3.1 + OTel
    implementation("io.micrometer:micrometer-tracing-bridge-otel")
    // 3. OTLP Exporter - To send data to OpenObserve
    implementation("io.opentelemetry:opentelemetry-exporter-otlp")
    // Align all Axon modules
    implementation(platform("org.axonframework:axon-bom:4.9.0"))
    implementation("org.axonframework:axon-spring-boot-starter")
    implementation("org.axonframework.extensions.kotlin:axon-kotlin")

    // Use gRPC BOM to align all gRPC modules with the version required by Hedera SDK
    implementation(platform("io.grpc:grpc-bom:1.73.0"))
    implementation("io.grpc:grpc-netty")
    implementation("io.grpc:grpc-core")
    implementation("io.grpc:grpc-protobuf")
    implementation("io.grpc:grpc-stub")

    // Ensure the connector is explicitly pulled at a compatible version (protobuf 4.x compatible)
    implementation("io.axoniq:axonserver-connector-java:2024.2.0")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")
    implementation("net.logstash.logback:logstash-logback-encoder:7.4")
    implementation("com.google.guava:guava:30.1-jre")
    implementation("com.google.protobuf:protobuf-java:4.31.1")
    implementation("com.hedera.hashgraph:sdk-full:2.60.0")
    //implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    implementation("org.javers:javers-spring-boot-starter-sql:7.7.0")
    testImplementation("junit:junit:4.13.1")
    implementation("org.postgresql:postgresql")
    implementation("com.h2database:h2")
    implementation("com.bartergrid:core:1.3.9")
    testImplementation("org.axonframework:axon-test:4.9.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

