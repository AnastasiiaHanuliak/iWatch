package com.ahanuliak.iwatch.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ahanuliak.iwatch.R
import com.ahanuliak.iwatch.data.entity.Movie
import com.ahanuliak.iwatch.ui.MovieViewModel
import com.ahanuliak.iwatch.util.IMAGE_URL
import com.squareup.picasso.Picasso

class MovieListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var movies = emptyList<Movie>()
    private var checkedMovies = ArrayList<Movie>()

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleItemView: TextView = itemView.findViewById(R.id.titleMovie)
        val releaseYearItemView: TextView = itemView.findViewById(R.id.release_year)
        val checkBoxItemView: CheckBox = itemView.findViewById(R.id.checkbox)
        val posterImgItemView: ImageView = itemView.findViewById(R.id.poster_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = inflater.inflate(R.layout.movies_list_item, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val current = movies[position]
        holder.titleItemView.text = current.title
        holder.releaseYearItemView.text = current.releaseYear

        Picasso.get()
            .load(IMAGE_URL + current.img)
            .into(holder.posterImgItemView)

        holder.checkBoxItemView.setOnClickListener(View.OnClickListener {
            if (holder.checkBoxItemView.isChecked) {
                checkedMovies.add(movies[position])
            }
            if (!holder.checkBoxItemView.isChecked) {
                checkedMovies.remove(movies[position])
            }
        })
    }

    internal fun deleteChecked(viewModel: MovieViewModel) {
        checkedMovies.forEach {
            viewModel.delete(it)
            Toast.makeText(inflater.context, it.title + " was deleted", Toast.LENGTH_SHORT).show()
        }
        checkedMovies.clear()
    }

    internal fun setMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun getItemCount() = movies.size
}
