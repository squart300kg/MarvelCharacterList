package kr.co.korean.ui.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import kr.co.korean.ui.R
import kr.co.korean.ui.model.CharactersUiModel
import kr.co.korean.util.DevicePreviews

@Composable
fun BaseCharacterItem(
    modifier: Modifier = Modifier,
    characterUiState: CharactersUiModel,
    onModifyingCharacterSavedStatus: (CharactersUiModel, Boolean) -> Unit,
    onDownloadThumbnail: (String) -> Unit = {}
) {
    var imageProgressState by remember { mutableStateOf(true) }

    Column {
        Box(modifier = modifier) {
            if (imageProgressState) {
                CircularProgressIndicator(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.characterItemHeight)),
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
            Image(
                modifier = modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.characterItemHeight))
                    .clickable {
                        onDownloadThumbnail(characterUiState.thumbnail)
                    },
                painter = rememberAsyncImagePainter(
                    model = characterUiState.thumbnail,
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

        Row {
            Image(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.bookmarkSize))
                    .clickable {
                        onModifyingCharacterSavedStatus(
                            characterUiState,
                            !characterUiState.saved
                        )
                    },
                painter = painterResource(id = characterUiState.bookMarkImage),
                contentDescription = null
            )

            Text(
                text = characterUiState.name,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            )
        }

        Text(
            modifier = Modifier,
            text = characterUiState.description.ifEmpty { stringResource(id = R.string.characterDescriptionEmpty) },
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
    }





























//    Box(
//        modifier = modifier
//            .fillMaxWidth()
//            .height(dimensionResource(id = R.dimen.characterItemHeight))
//            .padding(dimensionResource(id = R.dimen.characterItemCommonPadding))
//            .clickable {
//                onModifyingCharacterSavedStatus(
//                    characterUiState,
//                    !characterUiState.saved
//                )
//            }
//
//    ) {
//        Row(
//            modifier = Modifier
//                .align(Alignment.CenterStart),
//        ) {
//
//            if (imageProgressState) {
//                CircularProgressIndicator(
//                    modifier = Modifier,
//                    color = MaterialTheme.colorScheme.tertiary,
//                )
//            }
//            Image(
//                modifier = Modifier
////                    .weight(0.3f)
//                    .clickable {
//                        onDownloadThumbnail(characterUiState.thumbnail)
//                    },
//                painter = rememberAsyncImagePainter(
//                    model = characterUiState.thumbnail,
//                    onState = { state ->
//                        imageProgressState =
//                            when (state) {
//                                is AsyncImagePainter.State.Loading -> true
//                                is AsyncImagePainter.State.Empty,
//                                is AsyncImagePainter.State.Error,
//                                is AsyncImagePainter.State.Success -> false
//                            }
//                    }),
//                contentDescription = null
//            )
//
//            Column(
//                modifier = Modifier
//                    .padding(start = dimensionResource(R.dimen.characterItemCommonPadding))
////                    .weight(0.7f)
//                    .fillMaxHeight(),
//                verticalArrangement = Arrangement.SpaceBetween
//            ) {
//                Box(
//                    modifier = Modifier.fillMaxWidth().weight(0.1f)
//                ) {
//                    Text(
//                        modifier = Modifier.align(Alignment.CenterStart),
//                        text = characterUiState.name,
//                        style = TextStyle(
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 16.sp
//                        )
//                    )
//
//                    Image(
//                        modifier = Modifier
//                            .align(Alignment.CenterEnd)
//                            .size(dimensionResource(id = R.dimen.bookmarkSize)),
//                        painter = painterResource(id = characterUiState.bookMarkImage),
//                        contentDescription = null
//                    )
//                }
//
//                Text(
//                    modifier = Modifier
//                        .padding(top = dimensionResource(id = R.dimen.characterItemDescriptionTopMargin))
//                        .weight(0.6f),
//                    text = characterUiState.description.ifEmpty { stringResource(id = R.string.characterDescriptionEmpty) },
//                    overflow = TextOverflow.Ellipsis
//                )
//
////                Box(
////                    modifier = Modifier
////                        .weight(0.3f)
////                        .fillMaxWidth()
////                ) {
////                    Text(
////                        modifier = Modifier
////                            .align(Alignment.CenterStart),
////                        text = stringResource(id = R.string.characterItemComicCount) + characterUiState.comicCount.toString())
////                    Text(
////                        modifier = Modifier
////                            .align(Alignment.CenterEnd),
////                        text = stringResource(id = R.string.characterItemSeriesCount) + characterUiState.seriesCount.toString())
////                    Text(
////                        modifier = Modifier
////                            .align(Alignment.BottomStart),
////                        text = stringResource(id = R.string.characterItemStoryCount) + characterUiState.storyCount.toString())
////                    Text(
////                        modifier = Modifier
////                            .align(Alignment.BottomEnd),
////                        text = stringResource(id = R.string.characterItemEventCount) + characterUiState.eventCount.toString())
////
////                }
//
//            }
//        }
//    }
}

@DevicePreviews
@Composable
fun BaseCharacterItemPreview() {
    MaterialTheme {
        BaseCharacterItem(
            modifier = Modifier,
            characterUiState = CharactersUiModel(
                id = 1,
                thumbnail = "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available/portrait_xlarge.jpg",
                name = "hulk",
                description = "description hello world",
                urlCount = 1,
                comicCount = 1,
                storyCount = 1,
                eventCount = 1,
                seriesCount = 1,
                saved = true,
            ),
            onModifyingCharacterSavedStatus = {_, _ ->},
            onDownloadThumbnail = {}
        )
    }
}