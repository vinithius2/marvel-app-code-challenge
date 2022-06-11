package com.vinithius.marvelappchallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vinithius.datasource.repository.MarvelRepository
import com.vinithius.datasource.response.Character
import kotlinx.coroutines.flow.Flow

class MarvelViewModel(private val repository: MarvelRepository) : ViewModel() {

    private var currentResult: Flow<PagingData<Character>>? = null

    fun getCharacter(): Flow<PagingData<Character>> {
        val newResult: Flow<PagingData<Character>> =
            repository.getHeroes().cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }

}