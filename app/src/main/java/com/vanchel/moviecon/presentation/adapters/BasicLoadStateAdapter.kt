package com.vanchel.moviecon.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vanchel.moviecon.databinding.ListItemStateErrorBinding
import com.vanchel.moviecon.databinding.ListItemStateLoadingBinding

private const val VIEW_TYPE_ERROR = 1
private const val VIEW_TYPE_PROGRESS = 0

/**
 * @author Иван Тимашов
 *
 * Общий [LoadStateAdapter] для отображения сообщений об ошибках и индикаторов загрузки при
 * получении данных постранично.
 */
class BasicLoaderStateAdapter : LoadStateAdapter<RecyclerView.ViewHolder>() {
    override fun getStateViewType(loadState: LoadState) = when (loadState) {
        is LoadState.Loading -> VIEW_TYPE_PROGRESS
        is LoadState.Error -> VIEW_TYPE_ERROR
        is LoadState.NotLoading -> error("LoadState.NotLoading is not supported")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, loadState: LoadState) {
        when (loadState) {
            is LoadState.Loading -> Unit
            is LoadState.Error -> Log.e("VANCHEL_FTW", loadState.error.localizedMessage ?: "КУКЕЧ")
            is LoadState.NotLoading -> error("LoadState.NotLoading is not supported")
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): RecyclerView.ViewHolder {
        return when (loadState) {
            is LoadState.Loading -> ProgressViewHolder.from(parent)
            is LoadState.Error -> ErrorViewHolder.from(parent)
            is LoadState.NotLoading -> error("Not supported")
        }
    }

    /**
     * [RecyclerView.ViewHolder] для отображения индикатора загрузки.
     *
     * @constructor создает индикатор загрузки
     *
     * @param binding биндинг для макета загрузки
     */
    class ProgressViewHolder internal constructor(
        binding: ListItemStateLoadingBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            /**
             * Метод для создания [ProgressViewHolder] на основе родительской [ViewGroup].
             *
             * @param parent родительская [ViewGroup]
             * @return новый экземпляр [ProgressViewHolder]
             */
            fun from(parent: ViewGroup): ProgressViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemStateLoadingBinding.inflate(inflater, parent, false)
                return ProgressViewHolder(binding)
            }
        }
    }

    /**
     * [RecyclerView.ViewHolder] для отображения сообщения об ошибке.
     *
     * @constructor создает сообщение об ошибке
     *
     * @param binding биндинг для макета ошибки
     */
    class ErrorViewHolder internal constructor(
        binding: ListItemStateErrorBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            /**
             * Метод для создания [ErrorViewHolder] на основе родительской [ViewGroup].
             *
             * @param parent родительская [ViewGroup]
             * @return новый экземпляр [ErrorViewHolder]
             */
            fun from(parent: ViewGroup): ErrorViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemStateErrorBinding.inflate(inflater, parent, false)
                return ErrorViewHolder(binding)
            }
        }
    }
}