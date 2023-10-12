package kr.co.korean.network

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dagger.Reusable
import kr.co.korean.common.encodeToMd5
import kr.co.korean.network.model.CharactersResponseModel
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

const val CHARACTER_DATA_PAGE_SIZE = 20
const val STARTING_REFRESH_KEY_PAGE = 1

class MarvelCharacterPagingSource @Inject constructor(
    private val marvelCharacterApi: MarvelCharacterApi,
): PagingSource<Int, CharactersResponseModel.Data.Result>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharactersResponseModel.Data.Result> {
        try {
            val nextPage = params.key ?: 1
            val currentTimeMillis = System.currentTimeMillis()

            val result = marvelCharacterApi.getCharacters(
                apiKey = BuildConfig.marblePubKey,
                timeStamp = currentTimeMillis,
                hash = encodeToMd5("${currentTimeMillis}${BuildConfig.marblePrivKey}${BuildConfig.marblePubKey}"),
                offset = nextPage
            ).data.results

            return LoadResult.Page(
                data = result,
                prevKey = null,
                nextKey = nextPage + 1
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