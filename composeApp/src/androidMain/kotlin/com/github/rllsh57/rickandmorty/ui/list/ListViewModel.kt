package com.github.rllsh57.rickandmorty.ui.list

import androidx.lifecycle.*
import com.github.rllsh57.rickandmorty.data.network.api.CharacterApi
import com.github.rllsh57.rickandmorty.data.network.mapper.toModelList
import com.github.rllsh57.rickandmorty.domain.model.CharacterModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val characterApi: CharacterApi
) : ViewModel() {

    val state = MutableStateFlow<ListState>(ListState.Initial)

    init {
        fetchCharacters()
    }

    fun fetchCharacters() {
        viewModelScope.launchLoading {
            val result = characterApi.getCharacters()
            ListState.Result(result.results.toModelList())
        }
    }

    fun <T: ListState> CoroutineScope.launchLoading(block: suspend () -> T) {
        launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                state.value = ListState.Loading
                try {
                    state.value = block()
                } catch (error: Exception) {
                    Napier.e("launchLoading failed", error)
                    state.value = ListState.Error(error)
                }
            }
        }
    }
}

sealed class ListState {
    data object Initial : ListState()
    data object Loading : ListState()
    data class Result(val result: List<CharacterModel>) : ListState()
    data class Error(val error: Throwable) : ListState()
}