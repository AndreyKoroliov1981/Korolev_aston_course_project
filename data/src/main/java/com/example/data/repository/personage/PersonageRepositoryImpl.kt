package com.example.data.repository.personage

import android.util.Log
import com.example.data.network.personage.PersonageRetrofitService
import com.example.domain.characters.model.Response
import com.example.domain.episodes.model.Episode
import com.example.domain.personage.PersonageRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PersonageRepositoryImpl(
    private val episodeMapper: EpisodeMapper,
    private var personageRetrofitService: PersonageRetrofitService,
) : PersonageRepository {
    override suspend fun getEpisodes(queryString: String)
    : Response<List<Episode>>
    {
        val compositeDisposable = CompositeDisposable()
        var answer = Response<List<Episode>>(emptyList(),null)
        compositeDisposable.add(
            personageRetrofitService.getEpisodes(queryString)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response ->
                     answer =
                         Response(episodeMapper.mapEpisodeFromNetwork(response),null)
                    println("getEpisodes inside $answer")
                }, { t -> answer = Response(null, t.message) })
        )
        println("getEpisodes outside $answer")
        return answer
    }

//    private fun onFailure(response: Response<List<Episode>>) : Response<List<Episode>>{
//        Log.d("my_tag", "onFailure = ${response.errorText}")
//        return response
//    }
//
//    private fun onResponse(response: Response<List<Episode>>) : Response<List<Episode>> {
//        Log.d("my_tag", "onResponse = $response")
//        return response
////                MoviesAdapter(response.results)
//    }
}