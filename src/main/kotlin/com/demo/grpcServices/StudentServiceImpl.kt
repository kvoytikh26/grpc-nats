//package com.demo.grpcServices
//
//import kotlinx.coroutines.flow.Flow //---
//import com.demo.grpc.StudentServiceGrpcKt
//import com.demo.grpc.StudentServiceOuterClass
//import com.demo.model.Student
//import com.demo.service.StudentService
//import com.google.protobuf.Empty
//import kotlinx.coroutines.delay //---
//import kotlinx.coroutines.flow.flow //---
//import org.lognet.springboot.grpc.GRpcService
//import org.springframework.beans.factory.annotation.Autowired
//
//// use grpc reactor
//
//@GRpcService
//class StudentServiceImpl(
//    @Autowired
//    val studentService: StudentService
//) : StudentServiceGrpcKt.StudentServiceCoroutineImplBase() {
//
//   override fun findAllStudents(request: Empty): Flow<StudentServiceOuterClass.Student> {
//       val allStudents = studentService.findAllStudents()
//
//       return flow {
//           for (student in allStudents) {
//               delay(1000L)
//               emit(
//                   StudentServiceOuterClass.Student
//                       .newBuilder()
//                       .setFirstName(student.firstName)
//                       .setLastName(student.lastName)
//                       .build()
//               )
//           }
//       }
//   }
//
//    //make no-closed stream by id new entity - event bus -> nats
//    //project reactor in grpc
//
//    override suspend fun addNewStudent(request: StudentServiceOuterClass.Student)
//            : StudentServiceOuterClass.SucceedResponse {
//        val student = Student(firstName = request.firstName, lastName = request.lastName)
//
//        studentService.addNewStudent(student)
//
//        return StudentServiceOuterClass.SucceedResponse.newBuilder().setSucceeded(true).build()
//    }
//
////    override suspend fun editExistStudent(request: StudentServiceOuterClass.Student)
////    : StudentServiceOuterClass.SucceedResponse {
////        val optionalStudent = studentService.findFirstStudentByFirstNameAndLastName(request.firstName, request.lastName)
////
////
////    }
//}