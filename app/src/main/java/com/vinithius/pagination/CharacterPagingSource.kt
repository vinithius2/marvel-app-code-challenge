package com.vinithius.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vinithius.datasource.repository.MarvelRemoteDataSource
import com.vinithius.datasource.response.Character
import retrofit2.HttpException
import java.io.IOException

class CharacterPagingSource(
    private val marvelRemoteDataSource: MarvelRemoteDataSource,
) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val position = params.key ?: 0
        return try {
            val response = marvelRemoteDataSource.getHeroes(position)
            val characters = response.data.results
            LoadResult.Page(data = characters, prevKey = null, nextKey = position + 20)

        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        TODO("Not yet implemented")
    }

}