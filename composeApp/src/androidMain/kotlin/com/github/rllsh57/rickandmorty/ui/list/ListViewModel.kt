package com.github.rllsh57.rickandmorty.ui.list

import androidx.lifecycle.*
import com.github.rllsh57.rickandmorty.domain.model.CharacterModel
import com.github.rllsh57.rickandmorty.domain.model.PagedListModel
import com.github.rllsh57.rickandmorty.domain.usecase.FetchCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val fetchCharactersUseCase: FetchCharactersUseCase
) : ViewModel() {

    val state = MutableStateFlow<ListState>(ListState.Initial)

    init {
        fetchCharacters()
    }

    fun fetchCharacters(pagedList: PagedListModel<CharacterModel> = PagedListModel()) {
        state.value = ListState.Result(pagedList, true)
        viewModelScope.launchLoading {
            val result = fetchCharactersUseCase.execute(pagedList)
            ListState.Result(result, false)
        }
    }

    fun <T: ListState> CoroutineScope.launchLoading(block: suspend () -> T) {
        launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
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
    data class Result(val result: PagedListModel<CharacterModel>, val loading: Boolean) : ListState()
    data class Error(val error: Throwable) : ListState()
}