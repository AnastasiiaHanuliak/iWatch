package com.ahanuliak.iwatch.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.ahanuliak.iwatch.AddMovieActivity

import com.ahanuliak.iwatch.R
import com.ahanuliak.iwatch.data.response.Result
import com.ahanuliak.iwatch.util.IMAGE_URL
import com.squareup.picasso.Picasso


class SearchResultAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<SearchResultAdapter.MovieViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var movies = emptyList<Result>()

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleItemView: TextView = itemView.findViewById(R.id.titleMovie)
        val releaseYearItemView: TextView = itemView.findViewById(R.id.release_year)
        val descriptionItemView: TextView = itemView.findViewById(R.id.description)
        val posterImgItemView: ImageView = itemView.findViewById(R.id.poster_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = inflater.inflate(R.layout.search_result_item, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val current = movies[position]
        holder.titleItemView.text = current.title
        holder.releaseYearItemView.text = current.releaseDate
        holder.descriptionItemView.text = current.overview

        Picasso.get()
            .load(IMAGE_URL+ current.posterPath)
            .into(holder.posterImgItemView)

        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(inflater.context, AddMovieActivity::class.java)
            intent.putExtra("movie_title", movies[position].title)
            intent.putExtra("release_year", movies[position].releaseDate.substring(0,4))
            intent.putExtra("poster_path", movies[position].posterPath)
            inflater.context.startActivity(intent)
        })
    }

    internal fun setMovies(movies: List<Result>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun getItemCount() = movies.size
}



