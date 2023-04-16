package ru.madmax.bnettestcase.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.madmax.bnettestcase.databinding.ListItemBinding
import ru.madmax.bnettestcase.domain.model.Drag

class DragAdapter : PagingDataAdapter<Drag, DragAdapter.DragViewHolder>(REPO_COMPARATOR) {

    inner class DragViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Drag) {
            binding.apply {
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

    interface OnItemClickListener {
        fun onItemClick(item: Drag)
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Drag>() {
            override fun areItemsTheSame(oldItem: Drag, newItem: Drag): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Drag, newItem: Drag): Boolean =
                oldItem == newItem
        }
    }
}