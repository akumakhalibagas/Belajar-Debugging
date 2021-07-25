package com.makhalibagas.moviesaja.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.makhalibagas.moviesaja.data.MoviesTvAja
import com.makhalibagas.moviesaja.data.source.MoviesAjaRepository
import com.makhalibagas.moviesaja.data.source.remote.Person
import com.makhalibagas.moviesaja.data.source.remote.Trailer

class DetailViewModel(private val moviesAjaRepository: MoviesAjaRepository)  : ViewModel(){

    fun getDetailMovies(id : Int) : LiveData<MoviesTvAja> = moviesAjaRepository.getDetailMovies(id)
    fun getDetailTv(id : Int) : LiveData<MoviesTvAja> = moviesAjaRepository.getDetailTv(id)
    
    fun getActor(id : Int) : LiveData<List<Person>> = moviesAjaRepository.getActorMovies(id)
    fun getActorTv(id : Int) : LiveData<List<Person>> = moviesAjaRepository.getActorTv(id)

    fun getTrailer(id : Int) : LiveData<List<Trailer>> = moviesAjaRepository.getTrailer(id)
    fun getTrailerTv(id : Int) : LiveData<List<Trailer>> = moviesAjaRepository.getTrailerTv(id)
}