package com.github.rllsh57.rickandmorty.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.github.rllsh57.rickandmorty.domain.model.CharacterModel
import com.github.rllsh57.rickandmorty.domain.model.PagedListModel
import com.github.rllsh57.rickandmorty.ui.common.Loading
import com.github.rllsh57.rickandmorty.ui.common.TopBar

@Composable
fun ListScreen() {
    val viewModel = hiltViewModel<ListViewModel>()
    val state = viewModel.state.collectAsState().value

    Scaffold(
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
        topBar = { TopBar() }
    ) { contentPadding ->
        Column {
            when (state) {
                is ListState.Result -> ListResult(
                    result = state.result,
                    onLoadNextPage = { viewModel.fetchCharacters(it) },
                    contentPadding = contentPadding
                )

                is ListState.Initial -> ListEmpty()
                is ListState.Loading -> Loading()
                is ListState.Error -> ListError(
                    error = state.error
                )
            }
        }
    }
}

@Composable
fun ListEmpty() {
    Text("No Items")
}

@Composable
fun ListError(error: Throwable) {
    Text(
        text = error.message.orEmpty(),
        color = Color.Red
    )
}

@Composable
fun ListResult(
    result: PagedListModel<CharacterModel>,
    onLoadNextPage: (pagedList: PagedListModel<CharacterModel>) -> Unit,
    contentPadding: PaddingValues
) {
    Column {
        val loadedSize = result.items.size
        val totalSize = if (result.totalSize != Int.MAX_VALUE) result.totalSize else "?"
        Text(
            "Loaded number: $loadedSize, Total number: $totalSize",
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentPadding
        ) {
            val count = if (result.items.size < result.totalSize) result.items.size + 1 else result.items.size
            items(count) { index ->
                if (index == result.items.size) {
                    ListLoading()
                    LaunchedEffect(result) {
                        onLoadNextPage(result)
                    }
                } else {
                    CharacterItem(result.items[index])
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterItem(item: CharacterModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .height(50.dp)
            .fillMaxWidth()
    ) {
        GlideImage(
            model = item.image,
            contentDescription = item.name,
            modifier = Modifier
                .size(50.dp)
                .padding(horizontal = 6.dp, vertical = 2.dp)
        )
        Text(
            text = item.name
        )
    }
}

@Composable
fun ListLoading() {
    Box(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}