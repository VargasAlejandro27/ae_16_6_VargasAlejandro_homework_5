package com.pucetec.students.mappers

import com.pucetec.students.dto.ProfessorRequest
import com.pucetec.students.dto.ProfessorResponse
import com.pucetec.students.entities.Professor

fun ProfessorRequest.toEntity(): Professor {
    return Professor(
        name = name,
        email = email,
    )
}

fun Professor.toResponse(): ProfessorResponse {
    return ProfessorResponse(
        id = id,
        name = name,
        email = email,
    )
}
