package com.hkurbardovic.interactivereading.models

data class ClickableObject(
    val id: String?,
    val name: String?,
    val audioStorageLocation: String?,
    val coordinates: List<Int?>?
)