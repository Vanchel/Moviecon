package com.vanchel.moviecon.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vanchel.moviecon.R
import com.vanchel.moviecon.data.network.IMAGES_URL
import com.vanchel.moviecon.databinding.ListItemPosterBinding
import com.vanchel.moviecon.domain.entities.Image
import com.vanchel.moviecon.util.SIZE_POSTER_LARGE

/**
 * @author Иван Тимашов
 *
 *  Адаптер для отображения списка постеров.
 *
 * @property itemCallback интерфейс взаимодействия с элементом списка
 */
class PostersListAdapter(private val itemCallback: ItemCallback) :
    ListAdapter<Image, PostersListAdapter.ViewHolder>(ImageDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, itemCallback)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    /**
     * [RecyclerView.ViewHolder] для отображения [Image].
     *
     * @property binding биндинг для макета
     * @property callback интерфейс взаимодействия с элементом
     */
    class ViewHolder private constructor(
        private val binding: ListItemPosterBinding,
        private val callback: ItemCallback
    ) : RecyclerView.ViewHolder(binding.root) {
        /**
         * Метод привязки данных из экземпляра [Image] к представлению.
         *
         * @param item элемент [Image] для привязки
         */
        fun bind(item: Image) {
            binding.apply {
                val url = "$IMAGES_URL$SIZE_POSTER_LARGE${item.filePath}"
                Glide.with(root).load(url).placeholder(R.drawable.img_backdrop).into(imagePoster)
                cardView.setOnClickListener { callback.onItemSelected(item) }
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
                val binding = ListItemPosterBinding.inflate(inflater, parent, false)
                return ViewHolder(binding, callback)
            }
        }
    }

    /**
     * Реализация [DiffUtil.ItemCallback] для [Image], используемых в [PostersListAdapter].
     */
    class ImageDiffCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.filePath == newItem.filePath
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Интерфейс взаимодействия с элементом [Image], используемый в [PostersListAdapter].
     */
    interface ItemCallback {
        /**
         * Метод обработки выбора конкретного элемента списка.
         *
         * @param item выбранный элемент
         */
        fun onItemSelected(item: Image)
    }
}