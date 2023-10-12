package kr.co.korean.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kr.co.korean.common.encodeToMd5
import kr.co.korean.network.model.CharactersResponseModel
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

const val CHARACTER_DATA_PAGE_SIZE = 1

class MarvelCharacterPagingSource @Inject constructor(
    private val marvelCharacterApi: MarvelCharacterApi,
): PagingSource<Int, CharactersResponseModel.Data.Result>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharactersResponseModel.Data.Result> {
        try {
            val nextPage = params.key ?: 1
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
                nextKey = if (nextPage >= response.total) null else nextPage + 1
            )

        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    /**
     * swifeRefresh시, 데이터를 처음부터 보여주므로,
     * null을 리턴하여  [params.key]를 1로 반환받게합니다.
     */
    override fun getRefreshKey(state: PagingState<Int, CharactersResponseModel.Data.Result>): Int? {
        return null
    }
}