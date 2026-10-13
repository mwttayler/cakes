package com.maxtayler.cakes.ui.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maxtayler.cakes.data.model.Cake
import com.maxtayler.cakes.ui.components.ErrorMessage
import com.maxtayler.cakes.ui.components.LoadingIndicator
import com.maxtayler.cakes.ui.components.previews.LightDarkPreviews
import com.maxtayler.cakes.ui.list.components.CakeDescriptionDialog
import com.maxtayler.cakes.ui.list.components.CakesList
import com.maxtayler.cakes.ui.theme.CakesTheme

@Composable
fun CakesListScreen(modifier: Modifier = Modifier) {
    val viewModel: CakesListViewModel = hiltViewModel()
    val uiState: CakesListUiState by viewModel.uiState.collectAsStateWithLifecycle()
    CakesListContent(
        uiState = uiState,
        onRetryButtonClick = viewModel::onRetryButtonClicked,
        onCakeClicked = viewModel::onCakeClicked,
        onCakeDialogDismissed= viewModel::onCakeDialogDismissed,
        modifier = modifier.fillMaxSize()
    )
}

@Composable
private fun CakesListContent(
    uiState: CakesListUiState,
    onRetryButtonClick: () -> Unit,
    onCakeClicked: (cake: Cake) -> Unit,
    onCakeDialogDismissed: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is CakesListUiState.Error -> {
            ErrorMessage(
                onRetryButtonClick = onRetryButtonClick,
                modifier = modifier
            )
        }

        CakesListUiState.Loading -> {
            LoadingIndicator(modifier = modifier)
        }

        is CakesListUiState.Success -> {
            CakesList(
                cakes = uiState.cakes,
                onCakeClicked = onCakeClicked,
                modifier = modifier
            )
            uiState.selectedCake?.let { selectedCake ->
                CakeDescriptionDialog(
                    cake = selectedCake,
                    onDismiss = onCakeDialogDismissed
                )
            }
        }
    }
}

@LightDarkPreviews
@Composable
private fun CakesListScreenPreview(
    @PreviewParameter(CakesListUiStateProvider::class) uiState: CakesListUiState
) {
    CakesTheme {
        Surface {
            CakesListContent(
                uiState = uiState,
                onRetryButtonClick = { /* no-op */ },
                onCakeClicked = { /* no-op */ },
                onCakeDialogDismissed = { /* no-op */ },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

private class CakesListUiStateProvider : PreviewParameterProvider<CakesListUiState> {
    override val values: Sequence<CakesListUiState>
        get() = sequenceOf(
            CakesListUiState.Loading,
            CakesListUiState.Success(
                cakes = listOf(
                    Cake(
                        title = "Dundee Cake",
                        description = "A staple with Dundonians",
                        imageUrl = ""
                    )
                ),
                selectedCake = null
            ),
            CakesListUiState.Error
        )
}