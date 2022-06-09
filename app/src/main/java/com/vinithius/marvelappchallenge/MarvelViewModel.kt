package com.vinithius.marvelappchallenge

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vinithius.datasource.repository.MarvelRepository
import com.vinithius.datasource.response.Character
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MarvelViewModel(private val repository: MarvelRepository) : ViewModel() {

    private val _heroes = MutableLiveData<List<Character>>()
    val heroes: LiveData<List<Character>>
        get() = _heroes

    fun getHeroes() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.getHeroes().run {
                    _heroes.postValue(this.data.results)
                }
            } catch (e: Exception) {
                Log.e("Error list heroes", e.toString())
            }
        }
    }
}