package com.makhalibagas.moviesaja.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.makhalibagas.moviesaja.data.MoviesTvAja
import com.makhalibagas.moviesaja.data.source.remote.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesAjaRepository private constructor(private val remoteDataSource: RemoteDataSource) : MoviesAjaDataSource{

    companion object{
        @Volatile
        private var instance : MoviesAjaRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource) : MoviesAjaRepository =
                instance?: synchronized(this){
                    instance?: MoviesAjaRepository(remoteDataSource)
                }
    }

    override fun getPopularMovies(): LiveData<List<MoviesTvAja>> {
        val listMoviesAja = MutableLiveData<List<MoviesTvAja>>()
        CoroutineScope(Dispatchers.IO).launch {
            remoteDataSource.getPopularMovies(object : RemoteDataSource.loadPopularMoviesCallback{
                override fun onPopularMoviesReceived(moviesResponse: List<MoviesAja>) {
                    val moviesListAja = ArrayList<MoviesTvAja>()
                    for (moviesAja in moviesResponse){
                        val movies = MoviesTvAja(
                                moviesAja.id,
                                moviesAja.title,
                                moviesAja.overview,
                                moviesAja.posterPath,
                                moviesAja.backdropPath,
                                moviesAja.voteCount,
                                moviesAja.popularity,
                                moviesAja.releaseDate
                        )
                        moviesListAja.add(movies)
                    }
                    listMoviesAja.postValue(moviesListAja)
                }
            })
        }

        return listMoviesAja
    }

    override fun getPopularTv(): LiveData<List<MoviesTvAja>> {
        val listTvAja = MutableLiveData<List<MoviesTvAja>>()
        CoroutineScope(Dispatchers.IO).launch {
            remoteDataSource.getPopularTv(object : RemoteDataSource.loadPopularTvCallback{
                override fun onPopularTvReceived(tvResponse: List<TvShowsAja>) {
                    val tvListAja = ArrayList<MoviesTvAja>()
                    for (tvAja in tvResponse){

                        val movies = MoviesTvAja(
                                tvAja.id,
                                tvAja.name,
                                tvAja.overview,
                                tvAja.posterPath,
                                tvAja.backdropPath,
                                tvAja.voteCount,
                                tvAja.popularity,
                                tvAja.firstAirDate
                        )
                        tvListAja.add(movies)

                    }

                    listTvAja.postValue(tvListAja)
                }

            })
        }

        return listTvAja
    }

    override fun getDetailMovies(moviesId: Int): LiveData<MoviesTvAja> {

        val movies = MutableLiveData<MoviesTvAja>()
        CoroutineScope(Dispatchers.IO).launch {
            remoteDataSource.getDetailMovies(moviesId, object : RemoteDataSource.loadDetailMovies{
                override fun onDetailMoviesReceived(moviesAja: MoviesAja) {
                    val movie = MoviesTvAja(
                            moviesAja.id,
                            moviesAja.title,
                            moviesAja.overview,
                            moviesAja.posterPath,
                            moviesAja.backdropPath,
                            moviesAja.voteCount,
                            moviesAja.popularity,
                            moviesAja.releaseDate
                    )

                    movies.postValue(movie)
                }
            })
        }

        return movies
    }

    override fun getDetailTv(tvId: Int): LiveData<MoviesTvAja> {

        val tvshows = MutableLiveData<MoviesTvAja>()
        CoroutineScope(Dispatchers.IO).launch {
            remoteDataSource.getDetailTv(tvId, object : RemoteDataSource.loadDetailTv{
                override fun onDetailTvReceived(tvShowsAja: TvShowsAja) {
                    val tv = MoviesTvAja(
                            tvShowsAja.id,
                            tvShowsAja.name,
                            tvShowsAja.overview,
                            tvShowsAja.posterPath,
                            tvShowsAja.backdropPath,
                            tvShowsAja.voteCount,
                            tvShowsAja.popularity,
                            tvShowsAja.firstAirDate
                    )

                    tvshows.postValue(tv)
                }
            })
        }

        return tvshows
    }

    override fun getActorMovies(id : Int): LiveData<List<Person>> {
        val listPersonAja = MutableLiveData<List<Person>>()
        CoroutineScope(Dispatchers.IO).launch {
            remoteDataSource.getActorMovies(id, object : RemoteDataSource.loadActorMovies{
                override fun onActorMoviesReceived(listPerson: List<Person>) {
                    listPersonAja.postValue(listPerson)
                }
            })
        }
        return listPersonAja
    }

    override fun getActorTv(id : Int): LiveData<List<Person>> {
        val listPersonAja = MutableLiveData<List<Person>>()
        CoroutineScope(Dispatchers.IO).launch {
            remoteDataSource.getActorTv(id, object : RemoteDataSource.loadActorTv{
                override fun onActorTvReceived(listPerson: List<Person>) {
                    listPersonAja.postValue(listPerson)
                }
            })
        }
        return listPersonAja
    }

    override fun getTrailer(id: Int): LiveData<List<Trailer>> {
        val listTrailerAja = MutableLiveData<List<Trailer>>()
        CoroutineScope(Dispatchers.IO).launch {
            remoteDataSource.getTrailer(id, object : RemoteDataSource.loadTrailer{
                override fun onTrailerReceived(listTrailer: List<Trailer>) {
                    listTrailerAja.postValue(listTrailer)
                }

            })
        }

        return listTrailerAja
    }

    override fun getTrailerTv(id: Int): LiveData<List<Trailer>> {
        val listTrailerAja = MutableLiveData<List<Trailer>>()
        CoroutineScope(Dispatchers.IO).launch {
            remoteDataSource.getTrailerTv(id, object : RemoteDataSource.loadTrailerTv{
                override fun onTrailerTvReceived(listTrailer: List<Trailer>) {
                    listTrailerAja.postValue(listTrailer)
                }

            })
        }

        return listTrailerAja
    }

}
