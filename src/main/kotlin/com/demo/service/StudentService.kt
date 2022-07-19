package com.demo.service

import com.demo.model.Student
import com.demo.repository.ReactiveMongoStudentRepository
import com.demo.repository.StudentRepository
import lombok.AllArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.time.Duration
import javax.annotation.PostConstruct

@AllArgsConstructor
@Service
class StudentService (
    @Autowired
    val studentRepository: StudentRepository
) {
    @PostConstruct
    fun postConstruct() {
        println("Something done after init studentRepository bean")
    }

    fun findAllStudents(): MutableList<Student> = studentRepository.findAll()

    fun addNewStudent(student: Student) = studentRepository.insert(student)

    fun findStudentById(id: String) = studentRepository.findById(id)

    fun findFirstStudentByFirstNameAndLastName(firstName: String?, lastName: String?) = studentRepository
        .findFirstByFirstNameAndLastName(firstName, lastName)

//    fun findStudentByName(name: String?): Mono<Student> {
//        return studentRepository.findStudentsByFirstName(name)
//            .elementAt(1)
//            .map { student ->
//                Student(
//                    student.id,
//                    student.firstName?.uppercase(),
//                    student.lastName?.uppercase()
//                )
//            }
//            .delayElement(Duration.ofMillis(1300))
//            .timeout(Duration.ofSeconds(1400))
//            .retry(3)
//    }
}