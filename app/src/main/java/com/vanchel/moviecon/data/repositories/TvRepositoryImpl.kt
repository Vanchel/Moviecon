package com.vanchel.moviecon.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.vanchel.moviecon.data.network.models.*
import com.vanchel.moviecon.data.network.services.TvService
import com.vanchel.moviecon.data.paging.TvsPagingSource
import com.vanchel.moviecon.domain.entities.*
import com.vanchel.moviecon.domain.repositories.TvRepository
import com.vanchel.moviecon.util.PAGE_SIZE
import com.vanchel.moviecon.util.Schedulers
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * @author Иван Тимашов
 *
 * Основная реализация [TvRepository].
 *
 * @property tvService Источник данных о сериалах
 * @property schedulers Планировщики для выполнения асинхронных задач
 */
class TvRepositoryImpl @Inject constructor(
    private val tvService: TvService,
    private val schedulers: Schedulers
) : TvRepository {
    override fun getTvDetails(tvId: Int): Single<TvDetails> {
        return tvService.getDetails(tvId).map(TvDetailsResponse::transform)
    }

    override fun getTvCredits(tvId: Int): Single<List<Cast>> {
        return tvService.getCredits(tvId).map(CinematicCreditsResponse::transform)
    }

    override fun getTvImages(tvId: Int): Single<Images> {
        return tvService.getImages(tvId).map(ImagesResponse::transform)
    }

    override fun getTvsStream(type: TvType): Flowable<PagingData<Tv>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                TvsPagingSource(tvService, schedulers, type)
            }
        ).flowable
    }
}