package com.maxtayler.cakes.ui.list

import com.maxtayler.cakes.data.model.Cake

sealed interface CakesListUiState {

    data object Loading : CakesListUiState

    data class Success(
        val cakes: List<Cake>,
        val selectedCake: Cake?,
        val isRefreshing: Boolean
    ): CakesListUiState

    data object Error: CakesListUiState
}