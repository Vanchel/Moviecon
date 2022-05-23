package com.vanchel.moviecon.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vanchel.moviecon.R
import com.vanchel.moviecon.data.network.IMAGES_URL
import com.vanchel.moviecon.databinding.ListItemPanelCreditBinding
import com.vanchel.moviecon.domain.entities.Credit
import com.vanchel.moviecon.util.SIZE_BACKDROP_MEDIUM

/**
 * @author Иван Тимашов
 *
 *  Адаптер для отображения панели фильмов и сериалов.
 *
 * @property itemCallback интерфейс взаимодействия с элементом списка
 */
class CreditsPanelAdapter(private val itemCallback: ItemCallback) :
    ListAdapter<Credit, CreditsPanelAdapter.ViewHolder>(CreditDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, itemCallback)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    /**
     * [RecyclerView.ViewHolder] для отображения [Credit].
     *
     * @property binding биндинг для макета
     * @property callback интерфейс взаимодействия с элементом
     */
    class ViewHolder private constructor(
        private val binding: ListItemPanelCreditBinding,
        private val callback: ItemCallback
    ) : RecyclerView.ViewHolder(binding.root) {
        /**
         * Метод привязки данных из экземпляра [Credit] к представлению.
         *
         * @param item элемент [Credit] для привязки
         */
        fun bind(item: Credit) {
            binding.apply {
                textTitle.text = item.title
                item.backdropPath?.let {
                    val url = "$IMAGES_URL$SIZE_BACKDROP_MEDIUM$it"
                    Glide.with(root).load(url).placeholder(R.drawable.img_backdrop)
                        .into(imageBackdrop)
                } ?: imageBackdrop.setImageResource(R.drawable.img_backdrop)
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
                val binding = ListItemPanelCreditBinding.inflate(inflater, parent, false)
                return ViewHolder(binding, callback)
            }
        }
    }

    /**
     * Реализация [DiffUtil.ItemCallback] для [Credit], используемых в [CreditsPanelAdapter].
     */
    class CreditDiffCallback : DiffUtil.ItemCallback<Credit>() {
        override fun areItemsTheSame(oldItem: Credit, newItem: Credit): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Credit, newItem: Credit): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Интерфейс взаимодействия с элементом [Credit], используемый в [CreditsPanelAdapter].
     */
    interface ItemCallback {
        /**
         * Метод обработки выбора конкретного элемента списка.
         *
         * @param item выбранный элемент
         */
        fun onItemSelected(item: Credit)
    }
}