package com.demo

import com.demo.model.Address
import com.demo.model.Gender
import com.demo.model.Student
import com.demo.repository.StudentRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.annotation.PostConstruct

fun main(args: Array<String>) {
    runApplication<App>(*args)
}

@SpringBootApplication
class App(
    @Autowired
    val repository: StudentRepository
){

    @PostConstruct
    fun postConstructInit() {
        val address = Address(
            "Ukraine",
            "Rivne",
            "some"
        )
        val email = "kris5s@gmail.com"
        val student1 = Student(
            ObjectId(),
            "Kristina",
            "Voytikh",
            email,
            Gender.FEMALE,
            address,
            listOf("Math", "Computer SCience"),
            BigDecimal.TEN,
            LocalDateTime.now()
        )
        repository.save(student1)
        val student2 = Student(
            ObjectId(),
            "Kristina",
            "Voytikh",
            "kriss4@gmail.com",
            Gender.FEMALE,
            address,
            listOf("Computer"),
            BigDecimal.TEN,
            LocalDateTime.now()
        )
        repository.save(student2)

//			usingMongoTemplateAndQuery(repository, mongoTemplate, email, student1);
        repository.findFirstStudentByEmail(email)
            ?.ifPresentOrElse({ s: Student -> println("$s already exists") } as ((Student?) -> Unit)?) {
                println("Inserting student $student1")
                repository.insert(student1)
            }
    }

    private fun usingMongoTemplateAndQuery(
        repository: StudentRepository, mongoTemplate: MongoTemplate,
        email: String, student: Student
    ) {
        //--if you don't use repository
        val query = Query()
        query.addCriteria(Criteria.where("email").`is`(email))
        val students = mongoTemplate.find(query, Student::class.java)
        if (students.size <= 1) {
            ("found many students with email "
                    + email)
        }
        if (students.isEmpty()) {
            println("Inserting student $student")
            repository.insert(student)
        } else {
            println("$student already exists")
        }
    }
}