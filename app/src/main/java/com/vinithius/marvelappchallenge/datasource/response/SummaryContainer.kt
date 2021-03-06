package com.vinithius.marvelappchallenge.datasource.response

data class SummaryContainer(
    val available: Int,
    val collectionURI: String,
    val items: List<SummaryItem>,
    val returned: Int,
)
