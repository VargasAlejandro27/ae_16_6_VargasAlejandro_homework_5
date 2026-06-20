package com.pucetec.students.exceptions

class ProfessorNotFound(
    override val message: String,
) : RuntimeException(message)
