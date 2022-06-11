package com.vinithius.datasource.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.vinithius.pagination.CharacterPagingSource

class MarvelRepository(private val remoteDataSource: MarvelRemoteDataSource) {

    fun getHeroes(nameStartsWith: String?) =
        Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                CharacterPagingSource(remoteDataSource, nameStartsWith)
            }
        ).flow

}
