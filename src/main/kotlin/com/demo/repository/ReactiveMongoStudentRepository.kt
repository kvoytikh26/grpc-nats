package com.demo.repository

import com.demo.model.Student
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface ReactiveMongoStudentRepository: ReactiveMongoRepository<Student, String> {
    fun findStudentsByFirstName(firstName: String?): Flux<Student>
    fun findFirstStudentByEmail(email: String?): Mono<Student>?
    fun existsStudentByFirstNameAndLastName(firstName: String?, lastName: String?): Mono<Boolean>
}