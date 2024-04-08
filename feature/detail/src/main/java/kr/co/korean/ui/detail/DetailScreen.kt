package kr.co.korean.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import kr.co.korean.common.model.UiResult
import kr.co.korean.detail.R

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    viewModel: DetailViewModel = hiltViewModel()
) {

    val detailUiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (val uiState = detailUiState) {
        is UiResult.Error -> {}
        is UiResult.Loading -> {}
        is UiResult.Success -> {
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.characterItemCommonPadding))) {
                item {
                    var imageProgressState by remember { mutableStateOf(true) }

                    Text(
                        modifier = modifier,
                        text = uiState.model.name,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            color = Color.White
                        )
                    )

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.characterItemHeight))
                    ) {
                        if (imageProgressState) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(150.dp)
                                    .align(Alignment.Center),
                                color = MaterialTheme.colorScheme.tertiary,
                            )
                        }
                        Image(
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.Center),
                            painter = rememberAsyncImagePainter(
                                model = uiState.model.thumbnail,
                                contentScale = ContentScale.FillWidth,
                                onState = { state ->
                                    imageProgressState =
                                        when (state) {
                                            is AsyncImagePainter.State.Loading -> true
                                            is AsyncImagePainter.State.Empty,
                                            is AsyncImagePainter.State.Error,
                                            is AsyncImagePainter.State.Success -> false
                                        }
                                }),
                            contentDescription = null
                        )
                    }

                    Text(
                        modifier = modifier,
                        text = uiState.model.description,
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.White

                        )
                    )
                }
                items(
                    items = uiState.model.contents.toList(),
                    key = { it.first }
                ) { contents ->
                    val contentsType = contents.first
                    val contentsList = contents.second

                    Column(modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.characterItemCommonPadding))
                    ) {
                        Text(
                            text = contentsType.name,
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        contentsList.forEach { contentsName ->
                            Text(
                                text = contentsName,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Normal
                                ))
                        }
                    }
                }
            }
        }
    }
}