package com.vinithius.marvelappchallenge.datasource.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.vinithius.marvelappchallenge.datasource.response.Character
import com.vinithius.marvelappchallenge.pagination.CharacterPagingSource

class MarvelRepository(private val remoteDataSource: MarvelRemoteDataSource) {

    fun getCharactersList(nameStartsWith: String?) =
        Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                CharacterPagingSource(remoteDataSource, nameStartsWith)
            }
        ).liveData

    suspend fun getCharacterDetail(id: Int): Character? {
        return try {
            with(remoteDataSource) {
                val character = getCharacterDetail(id).data.results.first()
                character.comicsDetail = getCharacterDetailComics(id).data.results
                character.storiesDetail = getCharacterDetailStories(id).data.results
                character.seriesDetail = getCharacterDetailSeries(id).data.results
                character.eventsDetail = getCharacterDetailEvents(id).data.results
                character
            }
        } catch (e: NoSuchElementException) {
            null
        }
    }

}
