package com.pucetec.students.exceptions

class SubjectNotFound(
    override val message: String,
) : RuntimeException(message)
