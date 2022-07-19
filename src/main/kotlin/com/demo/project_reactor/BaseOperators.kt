package com.demo.project_reactor

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.io.IOException
import java.time.Duration.ofMillis
import java.util.function.Function


object BaseOperators {
    fun testTransform() {
        val filterAndMap = Function { f: Flux<String> ->
            f.filter { color -> color != "orange" }
                .map { obj -> obj.uppercase() }
        }
        Flux.fromIterable(listOf("blue", "green", "orange", "purple"))
            .doOnNext { x -> println(x) }
            .transform(filterAndMap)
            .subscribe { d -> println("Subscriber to Transformed MapAndFilter: $d") }
    }

    fun testMap() {
        Flux.just(1, 2, 3)
            .map { s -> s + 1 }
            .subscribe { x -> println(x) }
    }

    fun testFlatMap1() {
        Flux.just("1,2,3", "4,5,6")
            .flatMap { i ->
                Flux.fromIterable(i.split(","))
            }
            .collectList()
            .subscribe { x -> println(x) }
    }

    fun testFlatMap2() {
        Flux.range(1, 10)
            .flatMap { v ->
                if (v < 5) {
                    return@flatMap Flux.just(v * v)
                }
                Flux.error(IOException("Error: "))
            }
            .subscribe({ x -> println(x) }) { obj -> obj.printStackTrace() }
    }

    fun testFlatMap3() {
        val list = listOf("a", "b", "c", "d", "e", "f")
        Flux.fromIterable(list)
            .flatMap { s -> Flux.just(s + "x") }
            .collectList()
            .subscribe { x -> println(x) }
    }

    fun testFlatMap4() {
        Flux.just("Hello", "world")
            .flatMap { s ->
                Flux.fromIterable(s.split(""))
            }
            .subscribe {x -> println(x)}
    }

    fun compareConcatMapAndFlatMap() {
        println("Using flatMap():")

        Flux.range(1, 15)
            .flatMap { item ->
                Flux.just(item).delayElements(ofMillis(1))
            }
            .subscribe { x -> print("$x ") }

        Thread.sleep(100)

        println("\n\nUsing concatMap():")
        Flux.range(1, 15)
            .concatMap { item ->
                Flux.just(item).delayElements(ofMillis(1))
            }
            .subscribe { x -> print("$x ") }

        Thread.sleep(100)

        println("\n\nUsing switchMap():")
        Flux.range(1, 15)
            .switchMap { item ->
                Flux.just(item).delayElements(ofMillis(1))
            }
            .subscribe { x -> print("$x ") }

        Thread.sleep(100)
    }

    fun testStartWith() {
        Flux.range(1, 3)
            .startWith(Flux.just(9, 8, 7))
            .subscribe(System.out::println)
    }

    fun testCollectList() {
        Flux.range(1, 3)
            .collectList()
            .subscribe(System.out::println)
    }

    fun testMonoToFlux() {
        Mono.just(1)
            .flux()
            .subscribe(System.out::println)
    }

    fun testSortedList() {
        Flux.just(1, 4, 3, 5, 2)
            .collectSortedList()
            .subscribe(System.out::println)
    }
}

fun main() {
//    BaseOperators.testTransform()
//    BaseOperators.testMap()
//    BaseOperators.compareConcatMapAndFlatMap()
//    BaseOperators.testStartWith()
//    BaseOperators.testCollectList()
//    BaseOperators.testMonoToFlux()
    BaseOperators.testSortedList()
}