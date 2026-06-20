package com.pucetec.students.mappers

import com.pucetec.students.dto.EnrollmentResponse
import com.pucetec.students.entities.Enrollment

fun Enrollment.toResponse(): EnrollmentResponse {
    return EnrollmentResponse(
        id = id,
        createdAt = createdAt.toString(),
        status = status,
        student = student.toResponse(),
        subject = subject.toResponse(),
    )
}
