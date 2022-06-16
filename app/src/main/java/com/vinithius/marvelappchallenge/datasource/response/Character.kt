package com.vinithius.marvelappchallenge.datasource.response;

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Image,
    val resourceURI: String,
    val series: SummaryContainer,
    val stories: SummaryContainer,
    val events: SummaryContainer,
    val comics: SummaryContainer,
    var comicsDetail: List<GeneralDetailsCharacter>,
    var seriesDetail: List<GeneralDetailsCharacter>,
    var storiesDetail: List<GeneralDetailsCharacter>,
    var eventsDetail: List<GeneralDetailsCharacter>,
    val urls: List<Url>,
)
