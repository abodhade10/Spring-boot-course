package com.akhilbodhade.spring._boot_course.database.repository

import com.akhilbodhade.spring._boot_course.database.model.Note
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface NoteRepository: MongoRepository<Note, ObjectId> {
    fun findByOwnerId(ownerId: ObjectId): List<Note>
}
