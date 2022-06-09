package com.vinithius.datasource.repository

import com.vinithius.datasource.response.CharacterDataWrapper

class MarvelRepository(private val remoteDataSource: MarvelRemoteDataSource) {

    suspend fun getHeroes(): CharacterDataWrapper {
        return remoteDataSource.getHeroes()
    }

}
