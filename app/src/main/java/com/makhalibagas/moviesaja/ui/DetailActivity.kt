package com.makhalibagas.moviesaja.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.makhalibagas.moviesaja.adapter.PersonAdapter
import com.makhalibagas.moviesaja.adapter.TrailerAdapter
import com.makhalibagas.moviesaja.data.MoviesTvAja
import com.makhalibagas.moviesaja.data.source.remote.Person
import com.makhalibagas.moviesaja.data.source.remote.Trailer
import com.makhalibagas.moviesaja.databinding.ActivityDetailBinding
import com.makhalibagas.moviesaja.viewmodel.DetailViewModel
import com.makhalibagas.moviesaja.viewmodel.ViewModelFactory
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity(){

    private lateinit var viewModel : DetailViewModel
    companion object{
        const val base_url_backdrop = "https://image.tmdb.org/t/p/w780"
    }


    private val binding : ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this@DetailActivity, ViewModelFactory.getInstance())[DetailViewModel::class.java]

        val id = intent.getIntExtra("id", 0)
        val type = intent.getStringExtra("type")

        if (type.equals("tv")){
            viewModel.getDetailTv(id).observe(this, {
                showData(it)
            })

            viewModel.getActorTv(id).observe(this,{
                setupRvPerson(it)
            })

            viewModel.getTrailerTv(id).observe(this,{
                setupRvTrailer(it)
            })

        }else if (type.equals("movies")){
            viewModel.getDetailMovies(id).observe(this, {
                showData(it)
            })

            viewModel.getActor(id).observe(this,{
                setupRvPerson(it)
            })

            viewModel.getTrailer(id).observe(this,{
                setupRvTrailer(it)
            })

        }
    }

    @SuppressLint("SimpleDateFormat")
    fun showData(moviesAja: MoviesTvAja) {
        binding.tvName.text = moviesAja.name
        binding.tvOverview.text = moviesAja.desc
        binding.tvVote.text = moviesAja.vote_count.toString()
        binding.tvPopularity.text = moviesAja.popularity.toString()
        Glide.with(this).load(base_url_backdrop + moviesAja.backdrop).into(binding.backdrop)


        try {
            val date = SimpleDateFormat("yyyy-dd-MM").parse(moviesAja.release!!)
            val formater = SimpleDateFormat("dd MMMM")
            val newDateFormat: String = formater.format(date!!)
            binding.tvRelease.text = newDateFormat
        } catch (e: ParseException) {
            e.printStackTrace()
        }

    }

    fun setupRvPerson(it : List<Person>){
        binding.rvPerson.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.rvPerson.adapter = PersonAdapter(it)
    }

    fun setupRvTrailer(it : List<Trailer>){
        binding.rvTrailer.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.rvTrailer.adapter = TrailerAdapter(it)
    }

}