package com.atria.software.flashcardquiz

data class UserProfileData(
    val fullName: String,
    val userName: String,
    val phoneNumber: String,
    val code: String,
    val gender: String,
    val profileImageUrl: String? = null,
)