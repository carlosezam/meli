package com.ezam.melichallenge.search.di

import com.ezam.melichallenge.search.BuildConfig
import com.ezam.melichallenge.search.data.remote.SearchApi
import com.ezam.melichallenge.search.data.remote.model.SearchPaginationImpl
import com.ezam.melichallenge.search.data.repository.SearchRepositoryImpl
import com.ezam.melichallenge.search.domain.repository.SearchRepository
import com.ezam.melichallenge.search.domain.repository.model.SearchPagination
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
interface SearchRepositoryModule {

    @Binds
    fun bindsSearchRepository( impl: SearchRepositoryImpl) : SearchRepository<SearchPaginationImpl>



    companion object {
        @KotlinxSerializationConverterFactory
        @Provides
        fun providesJsonConverterFactory(): Converter.Factory {
            val json = Json {
                coerceInputValues = true
                ignoreUnknownKeys = true
                isLenient = true
            }
            return json.asConverterFactory("application/json".toMediaType())
        }

        @Provides
        fun providesRetrofit(
            @KotlinxSerializationConverterFactory converter: Converter.Factory
        ): Retrofit {

            val loggingInterceptor = HttpLoggingInterceptor().setLevel(
                if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BASIC
                else
                    HttpLoggingInterceptor.Level.NONE
            )

            val client = OkHttpClient
                .Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            return Retrofit
                .Builder()
                .client(client)
                .baseUrl(SearchApi.MELI_API_BASE_URL)
                .addConverterFactory(converter)
                .build()
        }

        @Provides
        fun providesSearchApi( retrofit: Retrofit ) = retrofit.create( SearchApi::class.java )

    }
}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class KotlinxSerializationConverterFactory