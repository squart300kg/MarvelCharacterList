package kr.co.korean.ui.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
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
import kr.co.korean.model.CharactersUiModel
import kr.co.korean.util.DevicePreviews

enum class ContentsType {
    Comics,
    Series,
    Stories,
    Events,
}

@Composable
fun BaseCharacterItem(
    modifier: Modifier = Modifier,
    characterUiState: CharactersUiModel,
    highlightSelectedItem: Boolean = false,
    onModifyingCharacterSavedStatus: (uiModel: CharactersUiModel, isSaved: Boolean) -> Unit,
    onDownloadThumbnail: (url: String) -> Unit = {},
    onNavigateToCharacterDetail: (type: ContentsType, id: Int) -> Unit = { _, _ -> },
) {
    var imageProgressState by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.characterItemCommonPadding))
            .background(
                if (highlightSelectedItem) MaterialTheme.colorScheme.surfaceVariant
                else Color.Transparent
            )
    ) {
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
                    .align(Alignment.Center)
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
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                text = characterUiState.name,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            )
        }

        Text(
            text = characterUiState.description.ifEmpty { stringResource(id = R.string.characterDescriptionEmpty) },
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )

        Row(
            modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.characterItemCountTopMargin))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf(
                Triple(R.string.characterItemComicCount, characterUiState.comicCount, ContentsType.Comics),
                Triple(R.string.characterItemSeriesCount, characterUiState.seriesCount, ContentsType.Series),
                Triple(R.string.characterItemStoryCount, characterUiState.storyCount, ContentsType.Stories),
                Triple(R.string.characterItemEventCount, characterUiState.eventCount, ContentsType.Events),
            ).forEach { triple ->
                val res = triple.first
                val count = triple.second
                val type = triple.third
                Text(
                    modifier = Modifier.clickable { onNavigateToCharacterDetail(type, characterUiState.id) },
                    text = stringResource(id = res) + count,
                    style = TextStyle(color = Color.Gray))
            }
        }

    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
internal fun <T> ThreePaneScaffoldNavigator<T>.isListPaneVisible(): Boolean =
    scaffoldValue[ListDetailPaneScaffoldRole.List] == PaneAdaptedValue.Expanded

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
internal fun <T> ThreePaneScaffoldNavigator<T>.isDetailPaneVisible(): Boolean =
    scaffoldValue[ListDetailPaneScaffoldRole.Detail] == PaneAdaptedValue.Expanded

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