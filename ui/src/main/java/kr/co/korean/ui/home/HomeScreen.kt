package kr.co.korean.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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

    val characterUiState by viewModel.characters.collectAsStateWithLifecycle()
    LazyColumn(modifier = modifier) {
        items(characterUiState) { uiModel ->
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

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .align(Alignment.CenterStart),
                ) {
                    Image(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .background(Color.Cyan)
                            .fillMaxWidth(0.5f)
                            .fillMaxHeight(),
                        painter = rememberAsyncImagePainter(uiModel.thumbnail),
                        contentDescription = null
                    )

                    Column(
                        modifier = Modifier
                            .padding(start = dimensionResource(id = R.dimen.characterItemCommonPadding))
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = modifier,
                            text = stringResource(id = R.string.characterItemUrlCount) + uiModel.urlCount.toString())
                        Text(
                            modifier = modifier,
                            text = stringResource(id = R.string.characterItemComicCount) + uiModel.comicCount.toString())
                        Text(
                            modifier = modifier,
                            text = stringResource(id = R.string.characterItemStoryCount) + uiModel.storyCount.toString())
                        Text(
                            modifier = modifier,
                            text = stringResource(id = R.string.characterItemEventCount) + uiModel.eventCount.toString())
                        Text(
                            modifier = modifier,
                            text = stringResource(id = R.string.characterItemSeriesCount) + uiModel.seriesCount.toString())
                    }
                }

                Image(
                    modifier = Modifier
                        .fillMaxWidth(0.1f)
                        .background(Color.Blue)
                        .align(Alignment.CenterEnd)
                        .padding(dimensionResource(id = R.dimen.characterItemCommonPadding)),
                    painter = painterResource(id = R.drawable.ic_bookmark_select),
                    contentDescription = null)
            }
        }

    }
}
