package com.vanchel.moviecon.domain.repositories

import androidx.paging.PagingData
import com.vanchel.moviecon.domain.entities.*
import kotlinx.coroutines.flow.Flow

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
    suspend fun getTvDetails(tvId: Int): TvDetails

    /**
     * Получает информацию о съемочной группе сериала.
     *
     * @param tvId Идентификатор сериала
     * @return Информация о съемочной группе сериала
     */
    suspend fun getTvCredits(tvId: Int): List<Cast>

    /**
     * Получает информацию об изображениях, приложенных к сериалу.
     *
     * @param tvId Идентификатор сериала
     * @return Информация об изображениях
     */
    suspend fun getTvImages(tvId: Int): Images

    /**
     * Получает поток сериалов, входящих в категорию [type].
     *
     * @param type Тип запрашиваемых сериалов
     * @return Поток сериалов из указанной категории
     */
    fun getTvsStream(type: TvType): Flow<PagingData<Tv>>
}