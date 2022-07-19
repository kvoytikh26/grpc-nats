package com.demo.controllers

import com.demo.model.Gender
import com.demo.model.Student
import com.demo.service.StudentService
import lombok.AllArgsConstructor
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.LocalDateTime


@RestController
@RequestMapping("/students")
@AllArgsConstructor
class StudentController (
    @Autowired
    val studentService: StudentService
) {

    @RequestMapping("/test")
    fun test() = Mono.just("Test!")

//    @GetMapping("/all")
//    fun getAllStudents(): Flux<Student> {
//        return studentService.findAllStudents()
//    }
//
//    @GetMapping("/id/{id}")
//    fun getStudentById(@PathVariable id: String): Mono<Student> = studentService.findStudentById(id)
//
//    @GetMapping("/name/{name}")
//    fun listStudent(@PathVariable name: String): Mono<Student> {
//        return studentService.findStudentByName(name)
//    }
//
//    @GetMapping("/save")
//    fun getStudentByName(): Flux<Student> {
//        return saveStudents()
//    }

    fun saveStudents(): Flux<Student> {

        val student1 = Student(
            ObjectId(),
            "Ola",
            "Voytikh",
            "kriss67@gmail.com",
            Gender.FEMALE,
            null,
            listOf("Math", "Computer SCience"),
            BigDecimal.TEN,
            LocalDateTime.now()
        )

        val student2 = Student(
            ObjectId(),
            "Kyryl",
            "Voytikh",
            "kriss67@gmail.com",
            Gender.FEMALE,
            null,
            listOf("Computer"),
            BigDecimal.TEN,
            LocalDateTime.now()
        )

        val students : Flux<Student> = Flux.just(student1, student2)
        return students
//        return students.flatMap {studentService.insertStudent(it)}
    }

    //rewrite http requests on grpc
    //grpc-reactor library
}