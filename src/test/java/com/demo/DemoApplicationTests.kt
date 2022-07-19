package com.demo

import com.demo.project_reactor.ReactorPractice
import com.demo.project_reactor.User
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.test.StepVerifier
import reactor.util.function.Tuple3
import java.time.Duration
import java.util.concurrent.CountDownLatch

@SpringBootTest
internal class DemoApplicationTests {

    companion object {
        val reactManager = ReactorPractice()
    }

    @Test
    fun simpleFluxExample() {
        val fluxColors = Flux.just("red", "green", "blue")
        //		fluxColors.log().subscribe(System.out::println);
        fluxColors.log()
    }

    @Test
    fun zipExample() {
        val fluxFruits = Flux.just("apple", "pear", "plum")
        val fluxColors = Flux.just("red", "green", "blue")
        val fluxAmounts = Flux.just(10, 20, 30)
        Flux.zip(fluxFruits, fluxColors, fluxAmounts).subscribe { x: Tuple3<String, String, Int>? -> println(x) }
    }

    @Test
    fun emptyFluxTest() {
        StepVerifier.create(reactManager.emptyFlux()).verifyComplete()
    }

    @Test
    fun fluxFooBarTest() {
        StepVerifier.create(reactManager.fluxFooBar())
            .expectNext("foo", "bar")
            .verifyComplete()
    }

    @Test
    fun fluxFromIterableTest() {
        StepVerifier.create(reactManager.fluxFromIterable())
            .expectNext("foo", "bar")
            .verifyComplete()
    }

    @Test
    fun fluxErrorTest() {
        StepVerifier.create(reactManager.fluxError()).verifyError()
    }

    @Test
    fun emptyMonoTest() {
        StepVerifier.create(reactManager.emptyMono()).verifyComplete()
    }

    @Test
    fun fluxWithUsersTest() {
        StepVerifier.create(reactManager.fluxWithUsers())
            .expectNextMatches { it.username == "swhite" }
            .expectNextMatches { it.username == "jpinkman" }
            .verifyComplete()
    }

    @Test
    fun monoUserMapTest() {
        val monoUser = Mono.just(User("jeremy", null, null, 20))

        StepVerifier.create(reactManager.monoUserMap(monoUser))
            .expectNextMatches { it.username == "JEREMY" }
            .expectComplete()
            .verify()
    }

    @Test
    fun testBlockingCode() {
        Flux.range(0, 5)
            .subscribeOn(Schedulers.parallel())
            .map{i -> {
                val latch = CountDownLatch(1)

                Mono.delay(Duration.ofMillis((i * 100).toLong()))
                    .subscribe()

                try {
                    latch.await()
                } catch (e: InterruptedException) {
                    println("EXCEPTION!")
                }
                println(i)
            }}.blockLast()
    }
}
