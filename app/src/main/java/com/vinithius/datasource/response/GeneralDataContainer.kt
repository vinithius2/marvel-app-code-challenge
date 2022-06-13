package com.vinithius.datasource.response

data class GeneralDataContainer(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<GeneralDetailsCharacter>
)
