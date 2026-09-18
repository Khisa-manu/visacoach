package com.visacoach.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.visacoach.data.local.TokenStorage
import com.visacoach.data.local.VisaCoachDatabase
import com.visacoach.data.remote.VisaCoachApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.visacoach.co.ke/"

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenStorage: TokenStorage): Interceptor {
        return Interceptor { chain ->
            val token = runBlocking { tokenStorage.accessToken.firstOrNull() }
            val requestBuilder = chain.request().newBuilder()
            if (!token.isNullOrBlank()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
            chain.proceed(requestBuilder.build())
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideVisaCoachApi(okHttpClient: OkHttpClient, gson: Gson): VisaCoachApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(VisaCoachApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): VisaCoachDatabase {
        return Room.databaseBuilder(
            context,
            VisaCoachDatabase::class.java,
            "visacoach.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideCachedQuestionDao(database: VisaCoachDatabase) = database.cachedQuestionDao()

    @Provides
    fun provideInterviewHistoryDao(database: VisaCoachDatabase) = database.interviewHistoryDao()
}
