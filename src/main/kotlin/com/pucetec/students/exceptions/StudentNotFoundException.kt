package com.pucetec.students.exceptions

class StudentNotFoundException(
    override val message: String,
) : RuntimeException(message)
