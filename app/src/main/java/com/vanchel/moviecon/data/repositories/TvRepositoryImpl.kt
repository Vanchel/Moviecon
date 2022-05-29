package com.vanchel.moviecon.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vanchel.moviecon.data.network.services.TvService
import com.vanchel.moviecon.data.paging.TvsPagingSource
import com.vanchel.moviecon.domain.entities.*
import com.vanchel.moviecon.domain.repositories.TvRepository
import com.vanchel.moviecon.util.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author Иван Тимашов
 *
 * Основная реализация [TvRepository].
 *
 * @property tvService Источник данных о сериалах
 */
class TvRepositoryImpl @Inject constructor(
    private val tvService: TvService
) : TvRepository {
    override suspend fun getTvDetails(tvId: Int): TvDetails {
        return tvService.getDetails(tvId).transform()
    }

    override suspend fun getTvCredits(tvId: Int): List<Cast> {
        return tvService.getCredits(tvId).transform()
    }

    override suspend fun getTvImages(tvId: Int): Images {
        return tvService.getImages(tvId).transform()
    }

    override fun getTvsStream(type: TvType): Flow<PagingData<Tv>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                TvsPagingSource(tvService, type)
            }
        ).flow
    }
}