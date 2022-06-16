package com.vinithius.datasource.response

data class GeneralDataWrapper(
    val code: Int,
    val status: String,
    val copyright: String,
    val data: GeneralDataContainer
)
