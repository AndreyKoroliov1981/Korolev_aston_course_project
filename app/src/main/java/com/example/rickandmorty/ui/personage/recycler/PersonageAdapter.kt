package com.example.rickandmorty.ui.personage.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.episodes.model.Episode
import com.example.rickandmorty.databinding.ItemEpisodeForPersonageBinding

class PersonageAdapter(
    private val onItemCLick: RVOnClickEpisodeListener,
    private val episodes: List<Episode>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EpisodeViewHolder(
            ItemEpisodeForPersonageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PersonageAdapter.EpisodeViewHolder -> {
                holder.bind(episodes[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return episodes.size
    }


    inner class EpisodeViewHolder(private val binding: ItemEpisodeForPersonageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.cvEpisode.setOnClickListener {
                onItemCLick.onClicked(episodes[absoluteAdapterPosition])
            }
        }

        fun bind(episode: Episode) {
            binding.tvName.text = episode.name
            binding.tvNumberEpisode.text = episode.episode
            binding.tvAirDate.text = episode.airDate

        }
    }
}