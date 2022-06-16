package com.vinithius.marvelappchallenge.datasource.repository

import com.vinithius.marvelappchallenge.datasource.response.CharacterDataWrapper
import com.vinithius.marvelappchallenge.datasource.response.GeneralDataWrapper
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MarvelRemoteDataSource {

    @GET("characters")
    suspend fun getCharactersList(
        @Query("offset") offset: Int,
        @Query("nameStartsWith") nameStartsWith: String?
    ): CharacterDataWrapper

    @GET("characters/{id}")
    suspend fun getCharacterDetail(
        @Path("id") id: Int
    ): CharacterDataWrapper

    @GET("characters/{id}/comics")
    suspend fun getCharacterDetailComics(
        @Path("id") id: Int
    ): GeneralDataWrapper

    @GET("characters/{id}/series")
    suspend fun getCharacterDetailSeries(
        @Path("id") id: Int
    ): GeneralDataWrapper

    @GET("characters/{id}/stories")
    suspend fun getCharacterDetailStories(
        @Path("id") id: Int
    ): GeneralDataWrapper

    @GET("characters/{id}/events")
    suspend fun getCharacterDetailEvents(
        @Path("id") id: Int
    ): GeneralDataWrapper

}
