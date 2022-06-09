package com.vinithius.datasource.response;

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Image,
    val resourceURI: String,
    val series: SummaryContainer,
    val stories: SummaryContainer,
    val events: SummaryContainer,
    val urls: List<Url>,
)
