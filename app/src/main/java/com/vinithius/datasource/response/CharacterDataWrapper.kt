package com.vinithius.datasource.response

data class CharacterDataWrapper(
    val code: Int,
    val status: String,
    val copyright: String,
    val data: CharacterDataContainer
)
