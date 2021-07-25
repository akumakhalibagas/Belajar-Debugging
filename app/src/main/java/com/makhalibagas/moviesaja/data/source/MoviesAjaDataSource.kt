package com.makhalibagas.moviesaja.data.source

import androidx.lifecycle.LiveData
import com.makhalibagas.moviesaja.data.MoviesTvAja
import com.makhalibagas.moviesaja.data.source.remote.Person
import com.makhalibagas.moviesaja.data.source.remote.Trailer

interface MoviesAjaDataSource {

    fun getPopularMovies() : LiveData<List<MoviesTvAja>>
    fun getPopularTv() : LiveData<List<MoviesTvAja>>

    fun getDetailMovies(moviesId : Int) : LiveData<MoviesTvAja>
    fun getDetailTv(tvId : Int) : LiveData<MoviesTvAja>

    fun getActorMovies(id : Int) : LiveData<List<Person>>
    fun getActorTv(id : Int) : LiveData<List<Person>>

    fun getTrailer(id : Int) : LiveData<List<Trailer>>
    fun getTrailerTv(id : Int) : LiveData<List<Trailer>>
}