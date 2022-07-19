package com.demo.model

import lombok.Data
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDateTime

@Document
data class Student(
    @Id
    val id: ObjectId? = null,

    val firstName: String? = null,
    val lastName: String? = null,

    val email: String? = null,

    val gender: Gender? = null,
    val address: Address? = null,
    val favouriteSubjects: List<String>? = null,
    val totalSpentInBooks: BigDecimal? = null,
    val createdTime: LocalDateTime? = null
)