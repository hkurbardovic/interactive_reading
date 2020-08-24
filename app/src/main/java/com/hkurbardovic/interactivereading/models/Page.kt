package com.hkurbardovic.interactivereading.models

data class Page(
    val imageStorageLocation: String?,
    val audioStorageLocation: String?,
    val localImageStorageLocation: String?,
    val localAudioStorageLocation: String?,
    val clickableObjects: List<ClickableObject?>?
)