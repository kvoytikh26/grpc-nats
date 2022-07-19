package com.demo.project_reactor

import reactor.core.publisher.Flux
import java.io.IOException
import java.util.*

object BaseOperators2 {
    fun testCollectMap() {
        Flux.just(1, 2, 3)
            .collectMap({ a -> "key: $a" }) { b -> "value: $b" }
            .subscribe { x -> println(x) }

    }

    fun testCollectMap2() {
        Flux.just("one", "three", "four")
            .collectMap({key -> key[0]}) {value -> value}
            .subscribe{x -> println(x)}
    }

    fun testCollectMultiMap() {
        Flux.just("one", "two", "three", "four", "five", "on")
            .collectMultimap({ a -> a[0] }) { b -> b }
            .subscribe{x -> println(x)}
    }

    fun testCollect() {
        Flux.just("one", "two", "three", "four", "five")
            .map { s -> s + "_new" }
            .collectList()
            .subscribe{x -> println(x)}

    }

    //convert all elements of thread in one object, use agr functions
    fun testReduce() {
        Flux.just(4, 5, 6)
            .reduce(Integer::sum)
            .subscribe{x -> println(x)}
    }

    fun testReduce2() {
        Flux.just(4, 5, 6)
            .reduce(Integer::max)
            .subscribe{x -> println(x)}
    }

    fun testReduce3() {
        Flux.just(4, 5, 6)
            .reduce { s1, s2 -> s1 - s2 }
            .subscribe { x -> println(x) }

    }

    fun testMap() {
        Flux.just(1, 2, 3)
            .map { s: Int -> s + 1 }
            .subscribe { x: Int? -> println(x) }
    }

    fun testFlatMap1() {
        Flux.just("1,2,3", "4,5,6")
            .flatMap { i: String ->
                Flux.fromIterable(Arrays.asList(*i.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()))
            }
            .subscribe { x -> println(x) }
    }

    fun testFlatMap2() {
        Flux.range(1, 10)
            .flatMap { v: Int ->
                if (v < 5) {
                    return@flatMap Flux.just(v * v)
                }
                Flux.error(IOException("Error: "))
            }
            .subscribe({ x -> println(x) }) { obj: Throwable -> obj.printStackTrace() }
    }

    fun testFlatMap3() {
        val list = listOf("a", "b", "c", "d", "e", "f")
        Flux.fromIterable(list)
            .flatMap { s: String -> Flux.just(s + "x") }
            .subscribe { x -> println(x) }
    }

    fun testFlatMap4() {
        Flux.just("Hello", "world")
            .flatMap { s ->
                Flux.fromArray(s.split("".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray())
            }
            .subscribe { x -> println(x) }
    }
}
fun main() {
//    BaseOperators2.testCollectMap2()
//    BaseOperators2.testCollectMultiMap()

//    BaseOperators2.testCollect()
//    BaseOperators2.testCollectMap()
//    BaseOperators2.testReduce()
//    BaseOperators2.testReduce2()
    BaseOperators2.testReduce3()
    BaseOperators2.testFlatMap3()
}