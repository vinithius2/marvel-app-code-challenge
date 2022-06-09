package com.vinithius.datasource.repository

import com.vinithius.datasource.response.CharacterDataWrapper
import retrofit2.http.GET


interface MarvelRemoteDataSource {

    @GET("characters")
    suspend fun getHeroes(): CharacterDataWrapper

}
