package com.maxtayler.cakes.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxtayler.cakes.data.model.Cake
import com.maxtayler.cakes.data.repository.CakesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
        load(isRefresh = false)
    }

    fun onRefresh() {
        val current = uiState.value as? CakesListUiState.Success ?: return
        if (current.isRefreshing) return
        load(isRefresh = true)
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
        load(isRefresh = false)
    }

    private fun load(isRefresh: Boolean) {
        job?.cancel()
        job = viewModelScope.launch {
            try {
                _uiState.update { state ->
                    if (isRefresh && state is CakesListUiState.Success) {
                        state.copy(isRefreshing = true)
                    } else {
                        CakesListUiState.Loading
                    }
                }

                val cakes = repository.getCakes()
                _uiState.update {
                    CakesListUiState.Success(
                        cakes = cakes,
                        selectedCake = null,
                        isRefreshing = false
                    )
                }
            } catch (exception: Exception) {
                ensureActive()
                // TODO: Replace printStackTrace with a proper logger such as Timber
                exception.printStackTrace()
                // TODO: Keep cakes on screen and show a snackbar when a refresh fails
                _uiState.update { CakesListUiState.Error }
            }
        }
    }
}