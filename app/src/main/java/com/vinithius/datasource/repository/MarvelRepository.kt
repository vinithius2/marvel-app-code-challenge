package com.vinithius.datasource.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.vinithius.datasource.response.Character
import com.vinithius.pagination.CharacterPagingSource

class MarvelRepository(private val remoteDataSource: MarvelRemoteDataSource) {

    fun getCharactersList(nameStartsWith: String?) =
        Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                CharacterPagingSource(remoteDataSource, nameStartsWith)
            }
        ).liveData

    suspend fun getCharacterDetail(id: Int): Character? {
        return try {
            remoteDataSource.getCharacterDetail(id).data.results.first()
        } catch (e: NoSuchElementException) {
            null
        }
    }

}
