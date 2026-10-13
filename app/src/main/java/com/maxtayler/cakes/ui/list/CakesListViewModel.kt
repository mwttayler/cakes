package com.maxtayler.cakes.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxtayler.cakes.data.model.Cake
import com.maxtayler.cakes.data.repository.CakesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CakesListViewModel @Inject constructor(
    private val repository: CakesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CakesListUiState>(CakesListUiState.Loading)
    val uiState: StateFlow<CakesListUiState> = _uiState

    private var job: Job? = null

    init {
        load()
    }

    fun onCakeClicked(cake: Cake) {
        _uiState.update { state ->
            if (state is CakesListUiState.Success) {
                state.copy(selectedCake = cake)
            } else {
                state
            }
        }
    }

    fun onCakeDialogDismissed() {
        _uiState.update { state ->
            if (state is CakesListUiState.Success) {
                state.copy(selectedCake = null)
            } else {
                state
            }
        }
    }

    fun onRetryButtonClicked() {
        load()
    }

    private fun load() {
        job?.cancel()
        job = viewModelScope.launch {
            try {
                _uiState.update { CakesListUiState.Loading }
                val cakes = repository.getCakes()
                _uiState.update {
                    CakesListUiState.Success(
                        cakes = cakes,
                        selectedCake = null
                    )
                }
            } catch (exception: Exception) {
                ensureActive()
                exception.printStackTrace()
                _uiState.update { CakesListUiState.Error }
            }
        }
    }
}