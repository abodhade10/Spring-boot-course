package com.akhilbodhade.spring._boot_course.controller

import com.akhilbodhade.spring._boot_course.database.model.Note
import com.akhilbodhade.spring._boot_course.database.repository.NoteRepository
import org.bson.types.ObjectId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

//Post//https://localhost:8085/notes
//GET//https://localhost:8085/notes?ownerId=123

@RestController
@RequestMapping("/notes")
class NoteController(
    val repository: NoteRepository
) {

    data class NoteRequest(
        val id: String?,
        val title: String,
        val content: String,
        val color: Long)

    data class NoteResponse(
        val id: String,
        val title: String,
        val content: String,
        val color: Long,
        val createdAt: Instant
    )

    @PostMapping
    fun save(
        @RequestBody body: NoteRequest): NoteResponse {
    val note = repository.save(Note(
    id = body.id?.let { ObjectId(it) }?: ObjectId.get(),
    title = body.title,
    content = body.content,
    color = body.color,
    createdAt = Instant.now(),
    ownerId = ObjectId()
))
        return note.toResponse()
    }

    @GetMapping
    fun findOwnerById(
        @RequestParam(required = true) ownerId: String): List<NoteResponse> {
        return repository.findByOwnerId(ObjectId(ownerId)).map{
            it.toResponse()
        }
    }

    private fun Note.toResponse(): NoteController.NoteResponse{
        return NoteResponse(
            id =   id.toHexString(),
            title = title,
            content = content,
            color = color,
            createdAt = createdAt)
    }
}