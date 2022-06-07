package com.vinithius.datasource.repository

import com.vinithius.datasource.response.MainRequest

class MarvelRepository(private val remoteDataSource: MarvelRemoteDataSource) {

    suspend fun getHeroes(): MainRequest {
        return remoteDataSource.getHeroes()
    }

}
