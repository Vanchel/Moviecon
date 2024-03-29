package com.vanchel.moviecon.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vanchel.moviecon.R
import com.vanchel.moviecon.databinding.ListItemCinematicTrendingBinding
import com.vanchel.moviecon.domain.entities.Tv
import com.vanchel.moviecon.util.BASE_URL_IMAGE
import com.vanchel.moviecon.util.SIZE_BACKDROP_MEDIUM

/**
 * @author Иван Тимашов
 *
 *  Адаптер для отображения сериалов на панели трендов.
 *
 * @property itemCallback интерфейс взаимодействия с элементом списка
 */
class TvsTrendingAdapter(private val itemCallback: ItemCallback) :
    PagingDataAdapter<Tv, TvsTrendingAdapter.ViewHolder>(TvDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, itemCallback)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let(holder::bind)
    }

    /**
     * [RecyclerView.ViewHolder] для отображения [Tv].
     *
     * @property binding биндинг для макета
     * @property callback интерфейс взаимодействия с элементом
     */
    class ViewHolder private constructor(
        private val binding: ListItemCinematicTrendingBinding,
        private val callback: ItemCallback
    ) : RecyclerView.ViewHolder(binding.root) {
        /**
         * Метод привязки данных из экземпляра [Tv] к представлению.
         *
         * @param item элемент [Tv] для привязки
         */
        fun bind(item: Tv) {
            binding.apply {
                item.backdropPath?.let {
                    val url = "$BASE_URL_IMAGE$SIZE_BACKDROP_MEDIUM$it"
                    Glide.with(root.context).load(url)
                        .placeholder(R.drawable.img_backdrop).into(imageBackdrop)
                } ?: imageBackdrop.setImageResource(R.drawable.img_backdrop)
                textTitle.text = item.name

                textRating.text = itemView.context.getString(
                    R.string.ratings_detailed, (item.voteAverage * 10).toInt()
                )

                root.setOnClickListener { callback.onItemSelected(item) }
            }
        }

        companion object {
            /**
             * Метод для создания [ViewHolder] на основе родительской [ViewGroup].
             *
             * @param parent родительская [ViewGroup]
             * @param callback интерфейс взаимодействия с элементом
             * @return новый экземпляр [ViewHolder]
             */
            fun from(parent: ViewGroup, callback: ItemCallback): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemCinematicTrendingBinding.inflate(inflater, parent, false)
                return ViewHolder(binding, callback)
            }
        }
    }

    /**
     * Реализация [DiffUtil.ItemCallback] для [Tv], используемых в [TvsTrendingAdapter].
     */
    class TvDiffCallback : DiffUtil.ItemCallback<Tv>() {
        override fun areItemsTheSame(oldItem: Tv, newItem: Tv): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Tv, newItem: Tv): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Интерфейс взаимодействия с элементом [Tv], используемый в [TvsTrendingAdapter].
     */
    interface ItemCallback {
        /**
         * Метод обработки выбора конкретного элемента списка.
         *
         * @param item выбранный элемент
         */
        fun onItemSelected(item: Tv)
    }
}