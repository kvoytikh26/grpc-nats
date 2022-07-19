package com.demo.repository

import com.demo.model.Student
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface StudentRepository: MongoRepository<Student, String> {
    fun findFirstStudentByEmail(email: String?): Optional<Student?>?
    fun findFirstByFirstNameAndLastName(firstName: String?, lastName: String?): Student
}