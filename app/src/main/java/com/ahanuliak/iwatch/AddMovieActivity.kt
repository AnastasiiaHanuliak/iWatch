package com.ahanuliak.iwatch

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.ahanuliak.iwatch.data.entity.Movie
import com.ahanuliak.iwatch.ui.MovieViewModel
import com.ahanuliak.iwatch.util.IMAGE_URL
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_movies.*

class AddMovieActivity : AppCompatActivity(){
    private lateinit var editTitleView: TextInputEditText
    private lateinit var editReleaseYearView: TextInputEditText
    private lateinit var imgView: ImageView
    private lateinit var movieViewModel: MovieViewModel

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movies)
        editTitleView = findViewById(R.id.edit_movie_title)
        editReleaseYearView = findViewById(R.id.edit_release_year)
        imgView = findViewById(R.id.movie_image)

        val button = findViewById<Button>(R.id.button_save)
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        if ( intent.hasExtra("movie_title"))
        {

            editTitleView.setText(intent.getStringExtra("movie_title").toString())
            editReleaseYearView.setText(intent.getStringExtra("release_year").toString())
            Picasso.get()
                .load(IMAGE_URL + intent.getStringExtra("poster_path").toString())
                .into(imgView)
        }

        button.setOnClickListener {
            val word = editTitleView.text.toString()
            val releaseYear = editReleaseYearView.text.toString()
            var img =""
            if ( intent.hasExtra("poster_path")) {
                img = intent.getStringExtra("poster_path")
            }
            movieViewModel.insert(Movie(word,releaseYear,img))
            val intent = Intent(this@AddMovieActivity, MainActivity::class.java)
            startActivity(intent)
        }

        search_btn.setOnClickListener{
            val intent = Intent(this@AddMovieActivity, SearchResultActivity::class.java)
            intent.putExtra("title", editTitleView.text.toString());
            startActivity(intent)
        }

    }
}