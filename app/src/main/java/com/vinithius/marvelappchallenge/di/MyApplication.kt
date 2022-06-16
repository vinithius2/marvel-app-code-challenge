package com.vinithius.marvelappchallenge.di

import com.vinithius.marvelappchallenge.datasource.repository.MarvelRemoteDataSource
import com.vinithius.marvelappchallenge.datasource.repository.MarvelRepository
import com.vinithius.marvelappchallenge.extension.hashMD5
import com.vinithius.marvelappchallenge.BuildConfig
import com.vinithius.marvelappchallenge.ui.MarvelViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val repositoryModule = module {
    single { get<Retrofit>().create(MarvelRemoteDataSource::class.java) }
}

val repositoryDataModule = module {
    single { MarvelRepository(get()) }
}

val viewModelModule = module {
    single { MarvelViewModel(get()) }
}

val networkModule = module {
    single { retrofit() }
}

fun retrofit(): Retrofit {

    val currentTimestamp = System.currentTimeMillis()
    val ts = currentTimestamp.toString()
    val privateKey = BuildConfig.PRIVATE_KEY
    val publicKey = BuildConfig.PUBLIC_KEY
    val hash = "$ts$privateKey$publicKey".hashMD5()

    val clientInterceptor = Interceptor { chain: Interceptor.Chain ->
        var request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("ts", ts)
            .addQueryParameter("apikey", publicKey)
            .addQueryParameter("hash", hash)
            .build()
        request = request.newBuilder().url(url).build()
        chain.proceed(request)
    }

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        .addNetworkInterceptor(clientInterceptor)
        .build()

    return Retrofit.Builder()
        .baseUrl("https://gateway.marvel.com/v1/public/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}
