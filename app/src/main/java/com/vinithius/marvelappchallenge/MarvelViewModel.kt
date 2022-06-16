package com.vinithius.marvelappchallenge

import android.util.Log
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

    private val _characterDetail = MutableLiveData<Character?>()
    val characterDetail: LiveData<Character?>
        get() = _characterDetail

    private val _characterDetailLoading = MutableLiveData<Boolean>()
    val characterDetailLoading: LiveData<Boolean>
        get() = _characterDetailLoading

    private val _characterDetailError = MutableLiveData<Boolean>().apply { value = false }
    val characterDetailError: LiveData<Boolean>
        get() = _characterDetailError

    private var _idCharacter: Int = 0

    fun setIdCharacter(value: Int) {
        _idCharacter = value
    }

    /**
     * Get character list using Paging3.
     */
    fun getCharactersList(nameStartsWith: String? = null): LiveData<PagingData<Character>>? {
        try {
            currentResult = repository.getCharactersList(nameStartsWith).cachedIn(viewModelScope)
        } catch (e: Exception) {
            Log.e("Error list heroes", e.toString())
        }
        return currentResult
    }

    fun getCharactersDetail() {
        CoroutineScope(Dispatchers.IO).launch {
            _characterDetailError.postValue(false)
            _characterDetailLoading.postValue(true)
            try {
                repository.getCharacterDetail(_idCharacter)?.run {
                    _characterDetail.postValue(this)
                }
            } catch (e: Exception) {
                _characterDetailError.postValue(true)
                Log.e("Error detail heroes", e.toString())
            } finally {
                _characterDetailLoading.postValue(false)
            }
        }
    }
}
