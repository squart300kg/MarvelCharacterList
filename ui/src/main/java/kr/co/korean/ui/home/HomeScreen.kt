package kr.co.korean.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import kr.co.korean.ui.R

// TODO:
//  1. 프리뷰 작업
//  2. 디바이스 크기게 맞게 컴포넌트 조절되도록 설정

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val characterUiState2 = viewModel.remoteCharacters.collectAsLazyPagingItems()
    LazyColumn(modifier = modifier) {
        items(characterUiState2.itemCount) { index  ->
            characterUiState2[index]?.let { characterUiState ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.characterItemCommonPadding))
                        .border(
                            width = 1.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.characterItemRoundCorner))
                        )
                        .height(dimensionResource(id = R.dimen.characterItemHeight))
                        .padding(dimensionResource(id = R.dimen.characterItemCommonPadding))
                        .clickable {
                            viewModel.modifyCharacterSavedStatus(
                                characterUiState.id
                            )
                        }

                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .align(Alignment.CenterStart),
                    ) {
                        Image(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
//                            .background(Color.Cyan)
                                .fillMaxWidth(0.5f)
                                .fillMaxHeight(),
                            painter = rememberAsyncImagePainter(characterUiState.thumbnail),
                            contentDescription = null
                        )

                        Column(
                            modifier = Modifier
                                .padding(start = dimensionResource(id = R.dimen.characterItemCommonPadding))
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            // TODO: uiState바꾸면서 중복 제거할 것
                            Text(
                                modifier = modifier,
                                text = stringResource(id = R.string.characterItemUrlCount) + characterUiState.urlCount.toString())
                            Text(
                                modifier = modifier,
                                text = stringResource(id = R.string.characterItemComicCount) + characterUiState.comicCount.toString())
                            Text(
                                modifier = modifier,
                                text = stringResource(id = R.string.characterItemStoryCount) + characterUiState.storyCount.toString())
                            Text(
                                modifier = modifier,
                                text = stringResource(id = R.string.characterItemEventCount) + characterUiState.eventCount.toString())
                            Text(
                                modifier = modifier,
                                text = stringResource(id = R.string.characterItemSeriesCount) + characterUiState.seriesCount.toString())
                        }
                    }

                    Image(
                        modifier = Modifier
                            .fillMaxWidth(0.2f)
                            .fillMaxHeight(0.5f)
                            .align(Alignment.CenterEnd),
                        painter = painterResource(id = R.drawable.ic_bookmark_select),
                        contentDescription = null)
                }

            }
        }
    }
}
