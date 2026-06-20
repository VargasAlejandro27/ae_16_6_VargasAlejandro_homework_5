package com.pucetec.students.services

import com.pucetec.students.dto.ProfessorRequest
import com.pucetec.students.dto.ProfessorResponse
import com.pucetec.students.entities.Professor
import com.pucetec.students.exceptions.ProfessorNotFound
import com.pucetec.students.exceptions.EmailAlreadyExistException
import com.pucetec.students.mappers.toEntity
import com.pucetec.students.mappers.toResponse
import com.pucetec.students.repositories.ProfessorRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ProfessorService(
    private val professorRepository: ProfessorRepository,
) {

    private val logger = LoggerFactory.getLogger(ProfessorService::class.java)

    fun createProfessor(request: ProfessorRequest): ProfessorResponse {
        if (request.name.isBlank()) {
            throw IllegalArgumentException("Professor name must not be blank")
        }
        if (request.email.isBlank()) {
            throw IllegalArgumentException("Professor email must not be blank")
        }
        if (professorRepository.existsByEmail(request.email)) {
            throw EmailAlreadyExistException("Professor email already exists")
        }

        val saved = professorRepository.save(request.toEntity())
        return saved.toResponse()
    }

    fun getAllProfessors(): List<ProfessorResponse> {
        return professorRepository.findAll().map { it.toResponse() }
    }

    fun getProfessorById(id: Long): ProfessorResponse {
        return professorRepository.findById(id)
            .orElseThrow { ProfessorNotFound("Professor with id $id not found") }
            .toResponse()
    }

    fun updateProfessor(id: Long, request: ProfessorRequest): ProfessorResponse {
        if (request.name.isBlank()) {
            throw IllegalArgumentException("Professor name must not be blank")
        }
        if (request.email.isBlank()) {
            throw IllegalArgumentException("Professor email must not be blank")
        }
        val professor = professorRepository.findById(id)
            .orElseThrow { ProfessorNotFound("Professor with id $id not found") }

        val updated = professorRepository.save(
            Professor(
                id = professor.id,
                name = request.name,
                email = request.email,
            )
        )

        return updated.toResponse()
    }

    fun deleteProfessor(id: Long) {
        val professor = professorRepository.findById(id)
            .orElseThrow { ProfessorNotFound("Professor with id $id not found") }
        professorRepository.delete(professor)
    }
}
