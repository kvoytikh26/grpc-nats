package com.demo.service

import com.demo.model.Student
import com.demo.repository.ReactiveMongoStudentRepository
import io.nats.client.Connection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.nio.charset.StandardCharsets

@Service
class ReactiveMongoStudentService(
    @Autowired
    val reactiveMongoStudentRepository: ReactiveMongoStudentRepository,
    val natsConnection: Connection
    ) {
    fun findAllStudents() = reactiveMongoStudentRepository.findAll()

    fun insertStudent(student: Student): Mono<Student> {
       return  reactiveMongoStudentRepository.insert(student)
           .flatMap { student ->
               natsConnection.publish("mongo.student.add",
                   "${student.id}|${student.firstName}|${student.lastName}".toByteArray(StandardCharsets.UTF_8))
               student.toMono()
           }

    }

    fun studentIsPresentByFirstNameAndLastName(firstName: String, lastName: String): Mono<Boolean> {
        return reactiveMongoStudentRepository.existsStudentByFirstNameAndLastName(firstName, lastName)
    }

}