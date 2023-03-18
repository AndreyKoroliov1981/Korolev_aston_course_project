package com.example.rickandmorty.ui.characters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.characters.model.Characters
import com.example.rickandmorty.databinding.ItemCharactersBinding

class CharactersAdapter(private val onItemCLick: RVOnClickCharactersListeners) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val differ = AsyncListDiffer(this, CharactersDiffUtilCallback())

    fun updateList(list: List<Characters>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CharactersHolder(
            ItemCharactersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CharactersHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }


    class CharactersDiffUtilCallback : DiffUtil.ItemCallback<Characters>() {
        override fun areItemsTheSame(oldItem: Characters, newItem: Characters): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Characters, newItem: Characters): Boolean {
            return oldItem == newItem
        }
    }

    inner class CharactersHolder(
        private val binding: ItemCharactersBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.cvCharacters.setOnClickListener {
                onItemCLick.onClicked(differ.currentList[absoluteAdapterPosition])
            }
        }

        fun bind(characters: Characters) {
            binding.apply {
                Glide.with(binding.root.context)
                    .load(characters.image)
                    .centerCrop()
                    .into(ivImage)
                tvName.text = characters.name
                tvSpecies.text = characters.species
                tvStatus.text = characters.status
                tvGender.text = characters.gender
            }
        }
    }
}