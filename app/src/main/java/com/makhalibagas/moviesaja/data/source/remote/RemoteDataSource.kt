package com.makhalibagas.moviesaja.data.source.remote

import com.makhalibagas.moviesaja.utils.EspressoIdlingResource
import retrofit2.await

class RemoteDataSource{

    companion object{
        @Volatile
        private var instance : RemoteDataSource? = null

        fun getInstance():RemoteDataSource =
                instance?: synchronized(this){
                    instance ?: RemoteDataSource()
                }
    }

    suspend fun getPopularMovies(callback: loadPopularMoviesCallback){
        EspressoIdlingResource.increment()
        tmdbServiceApi().getPopularMovies().await().results?.let {
            callback.onPopularMoviesReceived(it)
            EspressoIdlingResource.decrement()
        }
    }

    suspend fun getPopularTv(callback: loadPopularTvCallback){
        EspressoIdlingResource.increment()
        tmdbServiceApi().getPopularTv().await().results?.let {
            callback.onPopularTvReceived(it)
            EspressoIdlingResource.decrement()
        }
    }

    suspend fun getDetailMovies(id : Int, callback : loadDetailMovies){
        EspressoIdlingResource.increment()
        tmdbServiceApi().getDetailMovies(id).await().let {
            callback.onDetailMoviesReceived(it)
            EspressoIdlingResource.decrement()
        }
    }

    suspend fun getDetailTv(id : Int, callback : loadDetailTv){
        EspressoIdlingResource.increment()
        tmdbServiceApi().getDetailTv(id).await().let {
            callback.onDetailTvReceived(it)
            EspressoIdlingResource.decrement()
        }
    }

    suspend fun getActorMovies(id : Int, callback : loadActorMovies){
        EspressoIdlingResource.increment()
        tmdbServiceApi().getActorMovies(id).await().cast?.let {
            callback.onActorMoviesReceived(it)
            EspressoIdlingResource.decrement()
        }
    }

    suspend fun getActorTv(id : Int, callback : loadActorTv){
        EspressoIdlingResource.increment()
        tmdbServiceApi().getActorTv(id).await().cast?.let {
            callback.onActorTvReceived(it)
            EspressoIdlingResource.decrement()
        }
    }

    suspend fun getTrailerTv(id : Int, callback : loadTrailerTv){
        EspressoIdlingResource.increment()
        tmdbServiceApi().getTrailerTv(id).await().cast?.let {
            callback.onTrailerTvReceived(it)
            EspressoIdlingResource.decrement()
        }
    }

    suspend fun getTrailer(id: Int, callback: loadTrailer){
        EspressoIdlingResource.increment()
        tmdbServiceApi().getTrailerMovies(id).await().cast?.let {
            callback.onTrailerReceived(it)
            EspressoIdlingResource.decrement()
        }
    }


    interface loadPopularMoviesCallback{
        fun onPopularMoviesReceived(moviesResponse : List<MoviesAja>)
    }

    interface loadPopularTvCallback{
        fun onPopularTvReceived(tvResponse : List<TvShowsAja>)
    }

    interface loadDetailMovies{
        fun onDetailMoviesReceived(moviesAja: MoviesAja)
    }

    interface loadDetailTv{
        fun onDetailTvReceived(tvShowsAja: TvShowsAja)
    }

    interface loadActorMovies{
        fun onActorMoviesReceived(listPerson : List<Person>)
    }

    interface loadActorTv{
        fun onActorTvReceived(listPerson : List<Person>)
    }

    interface loadTrailer{
        fun onTrailerReceived(listTrailer : List<Trailer>)
    }

    interface loadTrailerTv{
        fun onTrailerTvReceived(listTrailer : List<Trailer>)
    }
}
