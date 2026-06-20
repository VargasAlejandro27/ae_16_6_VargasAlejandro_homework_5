package com.pucetec.students.services

import com.pucetec.students.dto.EnrollmentRequest
import com.pucetec.students.dto.EnrollmentResponse
import com.pucetec.students.dto.EnrollmentStatusRequest
import com.pucetec.students.entities.Enrollment
import com.pucetec.students.exceptions.EnrollmentNotFound
import com.pucetec.students.exceptions.StudentNotFoundException
import com.pucetec.students.exceptions.SubjectNotFound
import com.pucetec.students.mappers.toResponse
import com.pucetec.students.repositories.EnrollmentRepository
import com.pucetec.students.repositories.StudentRepository
import com.pucetec.students.repositories.SubjectRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class EnrollmentService(
    private val enrollmentRepository: EnrollmentRepository,
    private val studentRepository: StudentRepository,
    private val subjectRepository: SubjectRepository,
) {

    private val logger = LoggerFactory.getLogger(EnrollmentService::class.java)

    fun createEnrollment(request: EnrollmentRequest): EnrollmentResponse {
        val student = studentRepository.findById(request.studentId)
            .orElseThrow { StudentNotFoundException("Student with id ${request.studentId} not found") }

        val subject = subjectRepository.findById(request.subjectId)
            .orElseThrow { SubjectNotFound("Subject with id ${request.subjectId} not found") }

        val saved = enrollmentRepository.save(
            Enrollment(
                student = student,
                subject = subject,
            )
        )

        return saved.toResponse()
    }

    fun getAllEnrollments(): List<EnrollmentResponse> {
        return enrollmentRepository.findAll().map { it.toResponse() }
    }

    fun getEnrollmentById(id: Long): EnrollmentResponse {
        return enrollmentRepository.findById(id)
            .orElseThrow { EnrollmentNotFound("Enrollment with id $id not found") }
            .toResponse()
    }

    fun updateEnrollmentStatus(id: Long, request: EnrollmentStatusRequest): EnrollmentResponse {
        if (request.status.isBlank()) {
            throw IllegalArgumentException("Enrollment status must not be blank")
        }
        val enrollment = enrollmentRepository.findById(id)
            .orElseThrow { EnrollmentNotFound("Enrollment with id $id not found") }

        val updated = enrollmentRepository.save(
            Enrollment(
                id = enrollment.id,
                createdAt = enrollment.createdAt,
                status = request.status,
                student = enrollment.student,
                subject = enrollment.subject,
            )
        )

        return updated.toResponse()
    }

    fun deleteEnrollment(id: Long) {
        val enrollment = enrollmentRepository.findById(id)
            .orElseThrow { EnrollmentNotFound("Enrollment with id $id not found") }
        enrollmentRepository.delete(enrollment)
    }
}
