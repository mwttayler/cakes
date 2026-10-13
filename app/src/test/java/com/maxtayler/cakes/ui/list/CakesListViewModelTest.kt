package com.maxtayler.cakes.ui.list

import com.maxtayler.cakes.MainDispatcherRule
import com.maxtayler.cakes.data.model.Cake
import com.maxtayler.cakes.data.model.CakeFactory
import com.maxtayler.cakes.data.repository.FakeCakesRepository
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class CakesListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val appleCake = CakeFactory.create(title = "Apple pie")
    private val bananaCake = CakeFactory.create(title = "Banana cake")

    @Test
    fun `GIVEN load succeeds WHEN app is started THEN cakes are presented`() {
        val repository = FakeCakesRepository(cakes = listOf(appleCake, bananaCake))

        val viewModel = CakesListViewModel(repository)

        assertEquals(success(cakes = listOf(appleCake, bananaCake)), viewModel.uiState.value)
    }

    @Test
    fun `GIVEN load fails WHEN app is started THEN error is presented`() {
        val repository = FakeCakesRepository(error = IOException("No network"))

        val viewModel = CakesListViewModel(repository)

        assertEquals(CakesListUiState.Error, viewModel.uiState.value)
    }

    @Test
    fun `GIVEN cakes are presented WHEN refresh succeeds THEN updated cakes are presented`() {
        val repository = FakeCakesRepository(cakes = listOf(appleCake))
        val viewModel = CakesListViewModel(repository)
        repository.cakes = listOf(appleCake, bananaCake)

        viewModel.onRefresh()

        assertEquals(success(cakes = listOf(appleCake, bananaCake)), viewModel.uiState.value)
    }

    @Test
    fun `GIVEN cakes are presented WHEN cake clicked THEN cake is selected`() {
        val repository = FakeCakesRepository(cakes = listOf(appleCake, bananaCake))
        val viewModel = CakesListViewModel(repository)

        viewModel.onCakeClicked(bananaCake)

        assertEquals(
            success(cakes = listOf(appleCake, bananaCake), selectedCake = bananaCake),
            viewModel.uiState.value
        )
    }

    @Test
    fun `GIVEN cake is selected WHEN cake dialog dismissed THEN no cake is selected`() {
        val repository = FakeCakesRepository(cakes = listOf(appleCake, bananaCake))
        val viewModel = CakesListViewModel(repository)
        viewModel.onCakeClicked(bananaCake)

        viewModel.onCakeDialogDismissed()

        assertEquals(success(cakes = listOf(appleCake, bananaCake)), viewModel.uiState.value)
    }

    @Test
    fun `GIVEN error is presented WHEN retry succeeds THEN cakes are presented`() {
        val repository = FakeCakesRepository(error = IOException("No network"))
        val viewModel = CakesListViewModel(repository)
        repository.error = null
        repository.cakes = listOf(appleCake)

        viewModel.onRetryButtonClicked()

        assertEquals(success(cakes = listOf(appleCake)), viewModel.uiState.value)
    }

    private fun success(
        cakes: List<Cake>,
        selectedCake: Cake? = null
    ) = CakesListUiState.Success(
        cakes = cakes,
        selectedCake = selectedCake,
        isRefreshing = false
    )
}
