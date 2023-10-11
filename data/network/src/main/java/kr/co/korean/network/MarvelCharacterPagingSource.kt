package kr.co.korean.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kr.co.korean.common.encodeToMd5
import kr.co.korean.network.model.CharactersResponseModel
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

const val CHARACTER_DATA_PAGE_SIZE = 20
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

    // TODO: 구현해야함
    override fun getRefreshKey(state: PagingState<Int, CharactersResponseModel.Data.Result>): Int? {
        return null
    }


}