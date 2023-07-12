package com.ewide.test.gamecatalogue.di

import com.ewide.test.gamecatalogue.data.repository.GameRepository
import com.ewide.test.gamecatalogue.data.repository.GameRepositoryImpl
import com.ewide.test.gamecatalogue.data.source.remote.network.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideOkhttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    fun provideBaseUrl(): Retrofit.Builder {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder()
            .baseUrl("https://www.cheapshark.com/api/1.0/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }

    @Provides
    fun provideApiService(
        retrofit: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): ApiService {
        return retrofit
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    fun provideRepository(
        apiService: ApiService
    ): GameRepository {
        return GameRepositoryImpl(apiService)
    }
}