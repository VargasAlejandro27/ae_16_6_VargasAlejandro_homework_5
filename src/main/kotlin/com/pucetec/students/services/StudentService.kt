package com.pucetec.students.services

import com.pucetec.students.dto.StudentRequest
import com.pucetec.students.dto.StudentResponse
import com.pucetec.students.entities.Student
import com.pucetec.students.exceptions.EmailAlreadyExistException
import com.pucetec.students.exceptions.StudentNotFoundException
import com.pucetec.students.mappers.toEntity
import com.pucetec.students.mappers.toResponse
import com.pucetec.students.repositories.StudentRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val studentRepository: StudentRepository
) {

    private val logger = LoggerFactory.getLogger(StudentService::class.java)

    fun createStudent(request: StudentRequest): StudentResponse {
        logger.info("Creating student ${request.name}")
        if (request.name.isBlank()) {
            throw IllegalArgumentException("Student name must not be blank")
        }
        if (request.email.isBlank()) {
            throw IllegalArgumentException("Student email must not be blank")
        }
        if (studentRepository.existsByEmail(request.email)) {
            throw EmailAlreadyExistException("Student email already exists")
        }

        val savedStudent = studentRepository.save(request.toEntity())
        return savedStudent.toResponse()
    }

    fun getAllStudents(): List<StudentResponse> {
        logger.info("Getting all student list")
        return studentRepository.findAll().map { it.toResponse() }
    }

    fun getStudentById(id: Long): StudentResponse {
        return studentRepository.findById(id)
            .orElseThrow { StudentNotFoundException("Student with id $id not found") }
            .toResponse()
    }

    fun updateStudent(id: Long, request: StudentRequest): StudentResponse {
        if (request.name.isBlank()) {
            throw IllegalArgumentException("Student name must not be blank")
        }
        if (request.email.isBlank()) {
            throw IllegalArgumentException("Student email must not be blank")
        }

        val student = studentRepository.findById(id)
            .orElseThrow { StudentNotFoundException("Student with id $id not found") }

        val updatedStudent = studentRepository.save(
            Student(
                id = student.id,
                name = request.name,
                email = request.email,
            )
        )

        return updatedStudent.toResponse()
    }

    fun deleteStudent(id: Long) {
        val student = studentRepository.findById(id)
            .orElseThrow { StudentNotFoundException("Student with id $id not found") }
        studentRepository.delete(student)
    }
}