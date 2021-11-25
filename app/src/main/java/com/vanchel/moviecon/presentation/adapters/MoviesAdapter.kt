package com.vanchel.moviecon.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vanchel.moviecon.R
import com.vanchel.moviecon.databinding.ListItemCinematicBinding
import com.vanchel.moviecon.domain.entities.Movie
import com.vanchel.moviecon.util.BASE_URL_IMAGE
import com.vanchel.moviecon.util.SIZE_BACKDROP_MEDIUM

/**
 * @author Иван Тимашов
 *
 *  Адаптер для отображения списка фильмов.
 *
 * @property itemCallback интерфейс взаимодействия с элементом списка
 */
class MoviesAdapter(private val itemCallback: ItemCallback) :
    PagingDataAdapter<Movie, MoviesAdapter.ViewHolder>(MovieDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, itemCallback)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let(holder::bind)
    }

    /**
     * [RecyclerView.ViewHolder] для отображения [Movie].
     *
     * @property binding биндинг для макета
     * @property callback интерфейс взаимодействия с элементом
     */
    class ViewHolder private constructor(
        private val binding: ListItemCinematicBinding,
        private val callback: ItemCallback
    ) : RecyclerView.ViewHolder(binding.root) {
        /**
         * Метод привязки данных из экземпляра [Movie] к представлению.
         *
         * @param item элемент [Movie] для привязки
         */
        fun bind(item: Movie) {
            binding.apply {
                item.backdropPath?.let {
                    val url = "$BASE_URL_IMAGE$SIZE_BACKDROP_MEDIUM$it"
                    Glide.with(root.context).load(url)
                        .placeholder(R.drawable.img_backdrop).into(imageBackdrop)
                } ?: imageBackdrop.setImageResource(R.drawable.img_backdrop)
                textTitle.text = item.title
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
                val binding = ListItemCinematicBinding.inflate(inflater, parent, false)
                return ViewHolder(binding, callback)
            }
        }
    }

    /**
     * Реализация [DiffUtil.ItemCallback] для [Movie], используемых в [MoviesAdapter].
     */
    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Интерфейс взаимодействия с элементом [Movie], используемый в [MoviesAdapter].
     */
    interface ItemCallback {
        /**
         * Метод обработки выбора конкретного элемента списка.
         *
         * @param item выбранный элемент
         */
        fun onItemSelected(item: Movie)
    }
}