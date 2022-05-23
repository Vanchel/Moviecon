package com.vanchel.moviecon.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vanchel.moviecon.R
import com.vanchel.moviecon.data.network.IMAGES_URL
import com.vanchel.moviecon.databinding.ListItemTilePersonBinding
import com.vanchel.moviecon.domain.entities.Person
import com.vanchel.moviecon.util.SIZE_PROFILE_LARGE

/**
 * @author Иван Тимашов
 *
 *  Адаптер для отображения списка людей.
 *
 * @property itemCallback интерфейс взаимодействия с элементом списка
 */
class PeopleAdapter(private val itemCallback: ItemCallback) :
    PagingDataAdapter<Person, PeopleAdapter.ViewHolder>(PersonDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, itemCallback)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let(holder::bind)
    }

    /**
     * [RecyclerView.ViewHolder] для отображения [Person].
     *
     * @property binding биндинг для макета
     * @property callback интерфейс взаимодействия с элементом
     */
    class ViewHolder private constructor(
        private val binding: ListItemTilePersonBinding,
        private val callback: ItemCallback
    ) : RecyclerView.ViewHolder(binding.root) {
        /**
         * Метод привязки данных из экземпляра [Person] к представлению.
         *
         * @param item элемент [Person] для привязки
         */
        fun bind(item: Person) {
            binding.apply {
                item.profilePath?.let {
                    val url = "$IMAGES_URL$SIZE_PROFILE_LARGE$it"
                    Glide.with(root.context).load(url)
                        .placeholder(R.drawable.ic_round_person_24).into(imageBackdrop)
                } ?: imageBackdrop.setImageResource(R.drawable.ic_round_person_24)
                textName.text = item.name
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
                val binding = ListItemTilePersonBinding.inflate(inflater, parent, false)
                return ViewHolder(binding, callback)
            }
        }
    }

    /**
     * Реализация [DiffUtil.ItemCallback] для [Person], используемых в [PeopleAdapter].
     */
    class PersonDiffCallback : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Интерфейс взаимодействия с элементом [Person], используемый в [PeopleAdapter].
     */
    interface ItemCallback {
        /**
         * Метод обработки выбора конкретного элемента списка.
         *
         * @param item выбранный элемент
         */
        fun onItemSelected(item: Person)
    }
}