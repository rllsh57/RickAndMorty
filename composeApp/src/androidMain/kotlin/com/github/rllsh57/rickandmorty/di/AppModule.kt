package com.github.rllsh57.rickandmorty.di

import android.content.Context
import androidx.room.Room
import com.github.rllsh57.rickandmorty.data.database.CharacterDatabase
import com.github.rllsh57.rickandmorty.data.database.dao.CharacterDao
import com.github.rllsh57.rickandmorty.data.network.api.CharacterApi
import com.github.rllsh57.rickandmorty.data.repository.CharacterRepositoryImpl
import com.github.rllsh57.rickandmorty.domain.repository.CharacterRepository
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.aakira.napier.*
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    init {
        Napier.base(DebugAntilog())
    }

    @Provides
    fun provideJson(): Json = Json { ignoreUnknownKeys = true }

    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun provideOkHttp(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(json: Json, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(json.asConverterFactory(MEDIA_TYPE.toMediaType()))
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideCharactersApi(retrofit: Retrofit): CharacterApi = retrofit.create(CharacterApi::class.java)

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): CharacterDatabase = Room
        .databaseBuilder(context, CharacterDatabase::class.java, "characters_db")
        .build()

    @Singleton
    @Provides
    fun provideCountriesDao(database: CharacterDatabase): CharacterDao = database.characterDao()

    @Singleton
    @Provides
    fun bindCharactersRepository(repository: CharacterRepositoryImpl): CharacterRepository = repository

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
        const val MEDIA_TYPE = "application/json"
    }
}