package com.vanchel.moviecon.domain.repositories

import androidx.paging.PagingData
import com.vanchel.moviecon.domain.entities.*
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * @author Иван Тимашов
 *
 * Интерфейс получения данных о сериалах.
 */
interface TvRepository {
    /**
     * Получает подробную информацию о сериале.
     *
     * @param tvId Идентификатор сериала
     * @return Подробная инорфмация о сериале
     */
    fun getTvDetails(tvId: Int): Single<TvDetails>

    /**
     * Получает информацию о съемочной группе сериала.
     *
     * @param tvId Идентификатор сериала
     * @return Информация о съемочной группе сериала
     */
    fun getTvCredits(tvId: Int): Single<List<Cast>>

    /**
     * Получает информацию об изображениях, приложенных к сериалу.
     *
     * @param tvId Идентификатор сериала
     * @return Информация об изображениях
     */
    fun getTvImages(tvId: Int): Single<Images>

    /**
     * Получает поток сериалов, входящих в категорию [type].
     *
     * @param type Тип запрашиваемых сериалов
     * @return Поток сериалов из указанной категории
     */
    fun getTvsStream(type: TvType): Flowable<PagingData<Tv>>
}