import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*

plugins {
//	SpringBoot plugins
    id("org.springframework.boot") version "2.7.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

//	gRPC proto plugin (for generation gRPC classes)
    id("com.google.protobuf") version "0.8.18"

    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"

    application
}

group = "com.demo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

//	Project Reactor
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

//	Spring Data
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.projectlombok:lombok:1.18.22")
    implementation("junit:junit:4.13.1")

//	Tests dependencies
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")

//	gRPC dependencies
    implementation("io.github.lognet:grpc-spring-boot-starter:4.7.1") // adding @GRpcService

    implementation("com.google.protobuf:protobuf-kotlin:3.19.4")
    api("io.grpc:grpc-protobuf:1.44.0")
    api("com.google.protobuf:protobuf-java-util:3.19.4")
    api("com.google.protobuf:protobuf-kotlin:3.19.4")
    api("io.grpc:grpc-kotlin-stub:1.2.1")
    api("io.grpc:grpc-stub:1.44.0")
    runtimeOnly("io.grpc:grpc-netty:1.44.0")

//    Reactive gRPC
    implementation("com.salesforce.servicelibs:reactor-grpc:1.2.3")
    implementation("com.salesforce.servicelibs:reactor-grpc-stub:1.2.3")

    //    NATS
    implementation("io.nats:jnats:2.15.3")
}

// Set the Main class
application {
    mainClass.set("AppKt")
}

// Configuration for protobuf builder
protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.19.4"
    }

//    Base gRPC plugins
    plugins {
//        Java gRPC plugin
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.44.0"
        }

//        Kotlin gRPC plugin
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.2.1:jdk7@jar"
        }

//        Reactive gRPC plugin
        id("reactor") {
            artifact = "com.salesforce.servicelibs:reactor-grpc:1.2.3"
        }
    }

    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
                id("reactor")
            }
            it.builtins {
                id("kotlin")
            }
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}


// This solves problem with
// "Entry .proto is a duplicate but no duplicate handling strategy has been set"
tasks.withType<Copy>().all {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}