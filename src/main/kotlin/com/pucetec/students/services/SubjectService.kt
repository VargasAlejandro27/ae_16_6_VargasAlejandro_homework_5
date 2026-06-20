package com.pucetec.students.services

import com.pucetec.students.dto.SubjectRequest
import com.pucetec.students.dto.SubjectResponse
import com.pucetec.students.entities.Subject
import com.pucetec.students.exceptions.ProfessorNotFound
import com.pucetec.students.exceptions.SubjectNotFound
import com.pucetec.students.mappers.toEntity
import com.pucetec.students.mappers.toResponse
import com.pucetec.students.repositories.ProfessorRepository
import com.pucetec.students.repositories.SubjectRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SubjectService(
    private val subjectRepository: SubjectRepository,
    private val professorRepository: ProfessorRepository,
) {

    private val logger = LoggerFactory.getLogger(SubjectService::class.java)

    fun createSubject(request: SubjectRequest): SubjectResponse {
        if (request.name.isBlank()) {
            throw IllegalArgumentException("Subject name must not be blank")
        }
        if (request.code.isBlank()) {
            throw IllegalArgumentException("Subject code must not be blank")
        }
        val professor = professorRepository.findById(request.professorId)
            .orElseThrow { ProfessorNotFound("Professor with id ${request.professorId} not found") }

        val saved = subjectRepository.save(request.toEntity(professor))
        return saved.toResponse()
    }

    fun getAllSubjects(): List<SubjectResponse> {
        return subjectRepository.findAll().map { it.toResponse() }
    }

    fun getSubjectById(id: Long): SubjectResponse {
        return subjectRepository.findById(id)
            .orElseThrow { SubjectNotFound("Subject with id $id not found") }
            .toResponse()
    }

    fun updateSubject(id: Long, request: SubjectRequest): SubjectResponse {
        if (request.name.isBlank()) {
            throw IllegalArgumentException("Subject name must not be blank")
        }
        if (request.code.isBlank()) {
            throw IllegalArgumentException("Subject code must not be blank")
        }
        val subject = subjectRepository.findById(id)
            .orElseThrow { SubjectNotFound("Subject with id $id not found") }

        val professor = professorRepository.findById(request.professorId)
            .orElseThrow { ProfessorNotFound("Professor with id ${request.professorId} not found") }

        val updated = subjectRepository.save(
            Subject(
                id = subject.id,
                name = request.name,
                code = request.code,
                professor = professor,
            )
        )

        return updated.toResponse()
    }

    fun deleteSubject(id: Long) {
        val subject = subjectRepository.findById(id)
            .orElseThrow { SubjectNotFound("Subject with id $id not found") }
        subjectRepository.delete(subject)
    }
}
