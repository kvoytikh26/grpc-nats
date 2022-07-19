package com.demo.grpcServices

import com.demo.grpc.ReactorStudentServiceGrpc
import com.demo.grpc.StudentServiceOuterClass
import com.demo.model.Student
import com.demo.service.ReactiveMongoStudentService
import com.google.protobuf.Empty
import org.lognet.springboot.grpc.GRpcService
import org.springframework.beans.factory.annotation.Autowired
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import com.demo.grpc.StudentServiceOuterClass.Student as ProtoStudent

@GRpcService
class ReactiveGrpcStudentServiceImpl (
    @Autowired
    val reactiveMongoStudentService: ReactiveMongoStudentService
) : ReactorStudentServiceGrpc.StudentServiceImplBase() {

    @Override
    override fun addNewStudent(request: Mono<ProtoStudent>) : Mono<StudentServiceOuterClass.SucceedResponse>{
        return request.flatMap {
            reactiveMongoStudentService
                .insertStudent(
                    Student(
                        firstName = it.firstName,
                        lastName = it.lastName
                    )
                )
        }.mapToResponse()
    }

    override fun existsStudent(request: Mono<ProtoStudent>?):  Mono<StudentServiceOuterClass.SucceedResponse>? {
        return request?.let { mono ->
            mono.map { student ->
                val response = StudentServiceOuterClass.SucceedResponse.newBuilder()

                reactiveMongoStudentService.studentIsPresentByFirstNameAndLastName(
                    firstName = student.firstName, lastName = student.lastName)
                    .map {
                        response.succeeded = it

                        if (!it) {
                            response.error = "Not exist"
                        }
                    }.block()
                response.build()
            }
        }
    }




    @Override
    override fun findAllStudents(request: Mono<Empty>): Flux<ProtoStudent> {
        return reactiveMongoStudentService
            .findAllStudents()
            .map {
                mongoStudentToProtoStudent(it)
            }
    }

    fun Mono<Student>.mapToResponse(): Mono<StudentServiceOuterClass.SucceedResponse> {
        return map {
            StudentServiceOuterClass.SucceedResponse.newBuilder().setSucceeded(it is Student).build()
        }
    }

    fun mongoStudentToProtoStudent(student: Student): ProtoStudent {
        return ProtoStudent
            .newBuilder()
            .setFirstName(student.firstName)
            .setLastName(student.lastName)
            .build()
    }
}