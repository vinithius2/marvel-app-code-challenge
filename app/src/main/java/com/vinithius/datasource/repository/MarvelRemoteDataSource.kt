package com.vinithius.datasource.repository

import com.vinithius.datasource.response.CharacterDataWrapper
import retrofit2.http.GET
import retrofit2.http.Query


interface MarvelRemoteDataSource {

    @GET("characters")
    suspend fun getHeroes(@Query("offset") offset : Int): CharacterDataWrapper

}
