package com.vinithius.datasource.response;

data class HeaderHero(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<Hero>
)
