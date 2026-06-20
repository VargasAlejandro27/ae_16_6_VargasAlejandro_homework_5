package com.pucetec.students.mappers

import com.pucetec.students.dto.SubjectRequest
import com.pucetec.students.dto.SubjectResponse
import com.pucetec.students.entities.Subject

fun SubjectRequest.toEntity(professor: com.pucetec.students.entities.Professor): Subject {
    return Subject(
        name = name,
        code = code,
        professor = professor,
    )
}

fun Subject.toResponse(): SubjectResponse {
    return SubjectResponse(
        id = id,
        name = name,
        code = code,
        professor = professor.toResponse(),
    )
}
