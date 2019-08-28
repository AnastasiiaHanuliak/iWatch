package com.ahanuliak.iwatch

import com.ahanuliak.iwatch.data.response.MoviesResponce
import com.ahanuliak.iwatch.util.API_KEY
import com.ahanuliak.iwatch.util.BASE_URL
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceTMDB {

    @GET("3/search/movie")
    fun getMoviesByTitle(
        @Query("query") title: String
    ): Observable<MoviesResponce>

    companion object {
        fun create(): ApiServiceTMDB {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiServiceTMDB::class.java)
        }
    }
}