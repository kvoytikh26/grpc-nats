package com.demo.project_reactor

import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.SynchronousSink


    fun main() {
        val source = Flux.generate({ 0 }) { s: Int, sink: SynchronousSink<Int> ->
            println("Generating....")
            sink.next(s)
            s + 1
        }
        source
            .log("source")
                //32 - default queue elements for publisher for concatMap
            .concatMap { Mono.just(it.toString()) }
            .subscribe(object : Subscriber<Any?> {
                override fun onSubscribe(s: Subscription) {
//                    s.request(1) //ask n elements
                }

                override fun onNext(o: Any?) {}
                override fun onError(t: Throwable) {}
                override fun onComplete() {}
            })
        Thread.currentThread().join()
    }