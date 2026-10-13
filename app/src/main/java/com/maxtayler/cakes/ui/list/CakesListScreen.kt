package com.maxtayler.cakes.ui.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
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
        onRefresh = viewModel::onRefresh,
        onRetryButtonClick = viewModel::onRetryButtonClicked,
        onCakeClicked = viewModel::onCakeClicked,
        onCakeDialogDismissed= viewModel::onCakeDialogDismissed,
        modifier = modifier.fillMaxSize()
    )
}

@Composable
private fun CakesListContent(
    uiState: CakesListUiState,
    onRefresh: () -> Unit,
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
            val pullToRefreshState = rememberPullToRefreshState()
            PullToRefreshBox(
                isRefreshing = uiState.isRefreshing,
                state = pullToRefreshState,
                indicator = {
                    Indicator(
                        modifier = Modifier.align(Alignment.TopCenter),
                        isRefreshing = uiState.isRefreshing,
                        state = pullToRefreshState,
                        maxDistance = 120.dp
                    )
                },
                onRefresh = onRefresh
            ) {
                CakesList(
                    cakes = uiState.cakes,
                    onCakeClicked = onCakeClicked,
                    modifier = modifier
                )
            }
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
                onRefresh = { /* no-op */ },
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
                selectedCake = null,
                isRefreshing = false
            ),
            CakesListUiState.Error
        )
}