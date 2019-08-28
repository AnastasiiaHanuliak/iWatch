package com.ahanuliak.iwatch

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahanuliak.iwatch.ui.adapter.SearchResultAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchResultActivity: AppCompatActivity(){
    private lateinit var adapter: SearchResultAdapter
    private var disposable: Disposable? = null
    private val tmdbApiServe by lazy {
        ApiServiceTMDB.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        val recyclerView = findViewById<RecyclerView>(R.id.search_result_rv)
        adapter = SearchResultAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        beginSearch(intent.getStringExtra("title"))
    }

    private fun beginSearch(searchString: String) {
        disposable = tmdbApiServe.getMoviesByTitle(searchString)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> adapter.setMovies(result.results)},
                { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
            )
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}