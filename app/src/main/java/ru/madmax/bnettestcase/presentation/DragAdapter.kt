package ru.madmax.bnettestcase.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.madmax.bnettestcase.databinding.ListItemBinding
import ru.madmax.bnettestcase.domain.model.Drag
import ru.madmax.bnettestcase.util.Constants.BASE_IMAGE_URL

class DragAdapter : PagingDataAdapter<Drag, DragAdapter.DragViewHolder>(DragsDiffCallback()) {

    inner class DragViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Drag) {
            binding.apply {
                Glide
                    .with(itemImage.context)
                    .load(BASE_IMAGE_URL + item.image)
                    .into(itemImage)
                itemTitle.text = item.name
                itemDescription.text = item.description
            }
        }
    }

    override fun onBindViewHolder(holder: DragViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null)
            holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DragViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DragViewHolder(binding)
    }

    class DragsDiffCallback : DiffUtil.ItemCallback<Drag>() {
        override fun areItemsTheSame(oldItem: Drag, newItem: Drag): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Drag, newItem: Drag): Boolean =
            oldItem == newItem
    }
}