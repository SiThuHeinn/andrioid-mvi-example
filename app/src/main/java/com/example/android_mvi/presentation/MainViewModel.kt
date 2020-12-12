package com.example.android_mvi.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_mvi.Inent.MainIntent
import com.example.android_mvi.data.repositories.MainRepository
import com.example.android_mvi.viewstate.MainState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception

@ExperimentalCoroutinesApi
class MainViewModel(private val repository: MainRepository) : ViewModel() {

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state : StateFlow<MainState> get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect{
                when(it) {
                    is MainIntent.FetchPost -> { fetchPosts() }

                    //Can handle other user Intention here as well
                }
            }
        }
    }

    private fun fetchPosts(){
        viewModelScope.launch {
            _state.value = MainState.Loading
            _state.value = try {
                MainState.Posts(repository.fetchPosts())
            }catch (e : Exception){
                MainState.Error(e.localizedMessage)
            }
        }
    }
}