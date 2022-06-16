package com.vinithius.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vinithius.datasource.repository.MarvelRemoteDataSource
import com.vinithius.datasource.response.Character
import retrofit2.HttpException
import java.io.IOException


class CharacterPagingSource(
    private val marvelRemoteDataSource: MarvelRemoteDataSource,
    private val nameStartsWith: String?
) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val pageNumber = params.key ?: 0
        return try {
            val response = marvelRemoteDataSource.getCharactersList(pageNumber, nameStartsWith)
            val characters = response.data.results
            val prevKey = if (pageNumber > 0) pageNumber - OFFSET else null
            val nextKey = if (response.data.results.isNotEmpty()) pageNumber + OFFSET else null
            LoadResult.Page(data = characters, prevKey = prevKey, nextKey = nextKey)
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        TODO("Not yet implemented")
    }

    companion object {
        const val OFFSET = 20
    }

}