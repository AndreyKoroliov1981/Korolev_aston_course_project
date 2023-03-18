package com.example.rickandmorty.ui.episodes.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.episodes.model.Episode
import com.example.rickandmorty.databinding.ItemEpisodesBinding

class EpisodesAdapter(private val onItemCLick: RVOnClickEpisodesListeners) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val differ = AsyncListDiffer(this, EpisodeDiffUtilCallback())

    fun updateList(list: List<Episode>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EpisodesHolder(
            ItemEpisodesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EpisodesHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }


    class EpisodeDiffUtilCallback : DiffUtil.ItemCallback<Episode>() {
        override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem == newItem
        }
    }

    inner class EpisodesHolder(
        private val binding: ItemEpisodesBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.cvEpisodes.setOnClickListener {
                onItemCLick.onClicked(differ.currentList[absoluteAdapterPosition])
            }
        }

        fun bind(episode: Episode) {
            binding.apply {
                tvName.text = episode.name
                tvEpisode.text = episode.episode
                tvAirDate.text = episode.airDate
            }
        }
    }
}