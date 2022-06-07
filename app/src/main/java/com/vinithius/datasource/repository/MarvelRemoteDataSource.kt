package com.vinithius.datasource.repository

import com.vinithius.datasource.response.MainRequest
import retrofit2.http.GET


interface MarvelRemoteDataSource {

    @GET("characters")
    suspend fun getHeroes(): MainRequest

}
