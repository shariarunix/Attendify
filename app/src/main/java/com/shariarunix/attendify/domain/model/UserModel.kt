package com.shariarunix.attendify.domain.model

data class UserModel(
    val userID: String,
    val userName: String,
    val userEmail: String,
    val userPhone: String? = null,
    val userProfileImage: String? = null,
    val teacher: Boolean = false,
    val createdAt: String? = null
) {
    constructor() : this("", "", "")
}