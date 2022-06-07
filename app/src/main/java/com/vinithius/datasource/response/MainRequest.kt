package com.vinithius.datasource.response

data class MainRequest(
    val code: Int,
    val status: String,
    val copyright: String,
    val data: HeaderHero
)
