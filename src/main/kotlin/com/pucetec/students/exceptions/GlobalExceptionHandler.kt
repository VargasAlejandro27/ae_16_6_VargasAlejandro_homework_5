package com.pucetec.students.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(EmailAlreadyExistException::class)
    fun handleEmailAlreadyExistException(e: EmailAlreadyExistException): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            message = "Email already exists: ${e.message}",
        )
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(error)
    }

    @ExceptionHandler(ProfessorNotFound::class)
    fun handleProfessorNotFound(e: ProfessorNotFound): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            message = e.message,
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error)
    }

    @ExceptionHandler(SubjectNotFound::class)
    fun handleSubjectNotFound(e: SubjectNotFound): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            message = e.message,
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error)
    }

    @ExceptionHandler(EnrollmentNotFound::class)
    fun handleEnrollmentNotFound(e: EnrollmentNotFound): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            message = e.message,
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error)
    }

    @ExceptionHandler(StudentNotFoundException::class)
    fun handleStudentNotFoundException(e: StudentNotFoundException): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            message = e.message,
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            message = e.message ?: "Invalid request",
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error)
    }
}

data class ErrorResponse(
    val message: String,
    val timestamp: LocalDateTime = LocalDateTime.now()
)