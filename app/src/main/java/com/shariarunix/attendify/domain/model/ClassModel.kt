package com.shariarunix.attendify.domain.model

data class ClassModel(
    val classID: String,
    val classTitle: String,
    val classCode: String,
    val classDescription: String,
    val classDate: String,
    val classStartTime: String,
    val classEndTime: String,
    val classLocation: String,
    val classTeacherID: String,
    val studentIDs: List<String>,
) {
    constructor() : this("", "", "", "", "", "", "", "", "", emptyList())
}