package com.shariarunix.attendify.data.entity.classEntity

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ServerTimestamp

data class ClassEntity(
    val classId: String,
    val classTitle: String,
    val classCode: String,
    val classDescription: String,
    val classRepeat: Int, // 0 : Once. 1 : Daily. 2 : Custom
    val classDateTime: ClassDateTime?,
    val classLocation: String,
    val classTeacherId: String,
    val studentIds: List<String>,
    @ServerTimestamp val createdAt: FieldValue? = null,
    @ServerTimestamp val updatedAt: FieldValue? = null
) {
    constructor() : this("", "", "", "", 0, null, "", "", emptyList())
}
