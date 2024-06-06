package com.shariarunix.attendify.data.entity

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class UserEntity(
    val userID: String,
    val userName: String,
    val userEmail: String,
    val userPhone: String? = null,
    val userProfileImage: String? = null,
    val teacher: Boolean = false,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
) {
    constructor() : this("", "", "")
}