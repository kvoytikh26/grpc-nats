package com.demo.mongo_documents

import org.bson.codecs.pojo.annotations.BsonProperty
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Student(
    @BsonProperty("firstName")
    val firstName: String? = null,

    @BsonProperty("lastName")
    val lastName: String? = null
)