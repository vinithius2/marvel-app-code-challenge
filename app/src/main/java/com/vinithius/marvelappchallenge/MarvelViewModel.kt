package com.vinithius.marvelappchallenge

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vinithius.datasource.repository.MarvelRepository
import com.vinithius.datasource.response.Character
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MarvelViewModel(private val repository: MarvelRepository) : ViewModel() {

    var currentResult: LiveData<PagingData<Character>>? = null

    private val _characterDetail = MutableLiveData<Character>()
    val characterDetail: LiveData<Character>
        get() = _characterDetail

    private val _characterListLoading = MutableLiveData<Int>()
    val characterListLoading: LiveData<Int>
        get() = _characterListLoading

    private val _characterDetailLoading = MutableLiveData<Int>()
    val characterDetailLoading: LiveData<Int>
        get() = _characterDetailLoading

    private val _characterListError = MutableLiveData<Boolean>().apply { value = false }
    val characterListError: LiveData<Boolean>
        get() = _characterListError

    private val _characterDetailError = MutableLiveData<Boolean>().apply { value = false }
    val characterDetailError: LiveData<Boolean>
        get() = _characterDetailError

    fun getCharactersList(nameStartsWith: String? = null): LiveData<PagingData<Character>>? {
        _characterListLoading.value = View.VISIBLE
        try {
            currentResult = repository.getCharactersList(nameStartsWith).cachedIn(viewModelScope)
        } catch (e: Exception) {
            _characterListError.value = true
            Log.e("Error list heroes", e.toString())
        }
        return currentResult
    }

    fun finishLoading() {
        _characterListLoading.postValue(View.GONE)
    }

    fun getCharactersDetail(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            _characterDetailLoading.postValue(View.VISIBLE)
            try {
                repository.getCharacterDetail(id)?.run {
                    _characterDetail.postValue(this)
                }
            } catch (e: Exception) {
                _characterDetailError.postValue(true)
                Log.e("Error detail heroes", e.toString())
            } finally {
                _characterDetailLoading.postValue(View.GONE)
            }
        }
    }

}