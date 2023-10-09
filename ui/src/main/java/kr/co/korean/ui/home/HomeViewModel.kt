package kr.co.korean.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class CharacterUiModel(
    val thumbnail: String,
    val urlCount: Int,
    val comicCount: Int,
    val storyCount: Int,
    val eventCount: Int,
    val seriesCount: Int,
)

// TODO: 이미지 너비, 높이 계산 및 로딩 ContentScale방식 조정해야 함
@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    val characters = flow {
        emit(listOf(
            CharacterUiModel(
                thumbnail = "https://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg",
                urlCount = 3,
                comicCount = 3,
                storyCount = 3,
                eventCount = 3,
                seriesCount = 3
            ),CharacterUiModel(
                thumbnail = "https://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg",
                urlCount = 3,
                comicCount = 3,
                storyCount = 3,
                eventCount = 3,
                seriesCount = 3
            ),CharacterUiModel(
                thumbnail = "https://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg",
                urlCount = 3,
                comicCount = 3,
                storyCount = 3,
                eventCount = 3,
                seriesCount = 3
            ),CharacterUiModel(
                thumbnail = "https://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg",
                urlCount = 3,
                comicCount = 3,
                storyCount = 3,
                eventCount = 3,
                seriesCount = 3
            ),CharacterUiModel(
                thumbnail = "https://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg",
                urlCount = 3,
                comicCount = 3,
                storyCount = 3,
                eventCount = 3,
                seriesCount = 3
            ),CharacterUiModel(
                thumbnail = "https://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg",
                urlCount = 3,
                comicCount = 3,
                storyCount = 3,
                eventCount = 3,
                seriesCount = 3
            ),CharacterUiModel(
                thumbnail = "https://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg",
                urlCount = 3,
                comicCount = 3,
                storyCount = 3,
                eventCount = 3,
                seriesCount = 3
            )

        ))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = emptyList()
    )

}