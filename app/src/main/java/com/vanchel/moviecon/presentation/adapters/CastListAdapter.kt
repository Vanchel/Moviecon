package com.vanchel.moviecon.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vanchel.moviecon.R
import com.vanchel.moviecon.databinding.ListItemListCastBinding
import com.vanchel.moviecon.domain.entities.Cast
import com.vanchel.moviecon.util.BASE_URL_IMAGE
import com.vanchel.moviecon.util.SIZE_PROFILE_MEDIUM

/**
 * @author Иван Тимашов
 *
 *  Адаптер для отображения полного списка участников съемочной группы.
 *
 * @property itemCallback интерфейс взаимодействия с элементом списка
 */
class CastListAdapter(private val itemCallback: ItemCallback) :
    ListAdapter<Cast, CastListAdapter.ViewHolder>(CastDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, itemCallback)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    /**
     * [RecyclerView.ViewHolder] для отображения [Cast].
     *
     * @property binding биндинг для макета
     * @property callback интерфейс взаимодействия с элементом
     */
    class ViewHolder private constructor(
        private val binding: ListItemListCastBinding,
        private val callback: ItemCallback
    ) : RecyclerView.ViewHolder(binding.root) {
        /**
         * Метод привязки данных из экземпляра [Cast] к представлению.
         *
         * @param item элемент [Cast] для привязки
         */
        fun bind(item: Cast) {
            binding.apply {
                textName.text = item.name
                textCharacter.text = item.character
                item.profilePath?.let {
                    val url = "$BASE_URL_IMAGE$SIZE_PROFILE_MEDIUM$it"
                    Glide.with(root).load(url).placeholder(R.drawable.ic_round_person_24)
                        .into(imagePhoto)
                } ?: imagePhoto.setImageResource(R.drawable.ic_round_person_24)
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
                val binding = ListItemListCastBinding.inflate(inflater, parent, false)
                return ViewHolder(binding, callback)
            }
        }
    }

    /**
     * Реализация [DiffUtil.ItemCallback] для [Cast], используемых в [CastListAdapter].
     */
    class CastDiffCallback : DiffUtil.ItemCallback<Cast>() {
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Интерфейс взаимодействия с элементом [Cast], используемый в [CastListAdapter].
     */
    interface ItemCallback {
        /**
         * Метод обработки выбора конкретного элемента списка.
         *
         * @param item выбранный элемент
         */
        fun onItemSelected(item: Cast)
    }
}