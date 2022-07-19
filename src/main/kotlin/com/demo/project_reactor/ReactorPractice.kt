package com.demo.project_reactor

import com.demo.project_reactor.User.Companion.JESSE
import com.demo.project_reactor.User.Companion.SAUL
import com.demo.project_reactor.User.Companion.SKYLER
import com.demo.project_reactor.User.Companion.WALTER
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


class ReactorPractice {

    fun emptyFlux() = Flux.empty<Any>()

    fun fluxFooBar() = Flux.just("foo", "bar")

    fun fluxFromIterable() = Flux.fromIterable(listOf("foo", "bar"))

    fun fluxError() = Flux.error<Throwable>(RuntimeException())

    fun emptyMono() = Mono.empty<Any>()

    fun fluxWithUsers() = Flux.just(
        User("swhite", null, null, 20),
        User("jpinkman", null, null, 30)
    )

    fun monoUserMap(mono: Mono<User>): Mono<User> {
        return mono.map { user ->
            User(
                user.username?.let { it.uppercase() },
                user.firstname?.let { it.uppercase() },
                user.lastname?.let { it.uppercase() },
                user.age
            )
        }
    }

//    fun filterUsersAge(age: Int, users: Flux<User>){
//        Flux.range(0, 5)
//            .single()
//            .subscribeOn(Schedulers.parallel())
//            .subscribe()
//    }

    fun findOlderUser(users: Flux<User>) {

//        Flux.fromIterable(users)
//            .map(User::getAge)
//            .reduce(Integer::max)
//            .subscribe(System.out::println);
    }
}


fun main() {
    val flux1 = Flux.just(SKYLER, JESSE)
    val flux2 = Flux.just(WALTER, SAUL)

    val result = Flux.just(flux1, flux2).flatMap { it }
    result.subscribe(System.out::println)
}
