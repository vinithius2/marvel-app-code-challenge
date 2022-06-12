package com.vinithius.datasource.repository

import com.vinithius.datasource.response.CharacterDataWrapper
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

}
