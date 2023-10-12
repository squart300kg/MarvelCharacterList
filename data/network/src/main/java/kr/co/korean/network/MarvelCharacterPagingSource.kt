package kr.co.korean.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kr.co.korean.common.encodeToMd5
import kr.co.korean.network.model.CharactersResponseModel
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

const val CHARACTER_DATA_PAGE_SIZE = 20
const val STARTING_REFRESH_KEY_PAGE = 1

class MarvelCharacterPagingSource @Inject constructor(
    private val marvelCharacterApi: MarvelCharacterApi,
): PagingSource<Int, CharactersResponseModel.Data.Result>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharactersResponseModel.Data.Result> {
        try {
            val nextPage = 1550
//            val nextPage = params.key ?: 1
            val currentTimeMillis = System.currentTimeMillis()

            val response = marvelCharacterApi.getCharacters(
                apiKey = BuildConfig.marblePubKey,
                timeStamp = currentTimeMillis,
                hash = encodeToMd5("${currentTimeMillis}${BuildConfig.marblePrivKey}${BuildConfig.marblePubKey}"),
                offset = nextPage
            ).data

            return LoadResult.Page(
                data = response.results,
                prevKey = null,
                nextKey = if (nextPage >= response.limit) null else nextPage + 1
            )

        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharactersResponseModel.Data.Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.let { anchorPage ->
                anchorPage.prevKey?.plus(STARTING_REFRESH_KEY_PAGE) ?:
                anchorPage.nextKey?.minus(STARTING_REFRESH_KEY_PAGE)
            }
        }
    }
}