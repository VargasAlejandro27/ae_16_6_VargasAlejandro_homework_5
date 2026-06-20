package com.pucetec.students.exceptions

class EnrollmentNotFound(
    override val message: String,
) : RuntimeException(message)
