package com.example.flowmvvmexample.ui.main_activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.flowmvvmexample.common.utils.OnCharacterClick
import com.example.flowmvvmexample.databinding.RickMortyItemBinding
import com.example.flowmvvmexample.data.models.RickAndMortyItem
import kotlinx.coroutines.CoroutineScope

class CharacterAdapter : PagingDataAdapter<RickAndMortyItem, CharacterAdapter.ImageViewHolder>(
    diffCallback
) {

    inner class ImageViewHolder(
        val binding: RickMortyItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<RickAndMortyItem>() {
            override fun areItemsTheSame(
                oldItem: RickAndMortyItem,
                newItem: RickAndMortyItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: RickAndMortyItem,
                newItem: RickAndMortyItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var onItemClickListener: OnCharacterClick? = null


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currChar = getItem(position)

        holder.binding.apply {

            holder.itemView.apply {
                characterName.text = "${currChar?.name}"

                characterImage.setOnClickListener {
                    onItemClickListener?.onClick(currChar!!.id)
                }

                val imageLink = currChar?.image
                characterImage.load(imageLink) {
                    crossfade(true)
                    crossfade(1000)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            RickMortyItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    fun setOnItemClickListener(listener: OnCharacterClick) {
        onItemClickListener = listener
    }


}