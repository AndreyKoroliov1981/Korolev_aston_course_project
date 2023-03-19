package com.example.rickandmorty.ui.locations.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.episodes.model.Episode
import com.example.domain.locations.model.Locations
import com.example.rickandmorty.databinding.ItemEpisodesBinding
import com.example.rickandmorty.databinding.ItemLocationsBinding

class LocationsAdapter(private val onItemCLick: RVOnClickLocationsListeners) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val differ = AsyncListDiffer(this, LocationsDiffUtilCallback())

    fun updateList(list: List<Locations>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LocationsHolder(
            ItemLocationsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LocationsHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }


    class LocationsDiffUtilCallback : DiffUtil.ItemCallback<Locations>() {
        override fun areItemsTheSame(oldItem: Locations, newItem: Locations): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Locations, newItem: Locations): Boolean {
            return oldItem == newItem
        }
    }

    inner class LocationsHolder(
        private val binding: ItemLocationsBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.cvLocations.setOnClickListener {
                onItemCLick.onClicked(differ.currentList[absoluteAdapterPosition])
            }
        }

        fun bind(locations: Locations) {
            binding.apply {
                tvName.text = locations.name
                tvType.text = locations.type
                tvDimension.text = locations.dimension
            }
        }
    }
}