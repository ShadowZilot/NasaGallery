package com.human_developing_app.nasa_gallery.di

import android.content.Context
import com.human_developing_app.nasa_gallery.apod.data.ApodApi
import com.human_developing_app.nasa_gallery.apod.data.ApodLocalRepository
import com.human_developing_app.nasa_gallery.apod.data.ApodLocalRepositoryImpl
import com.human_developing_app.nasa_gallery.apod.data.ApodRepository
import com.human_developing_app.nasa_gallery.apod.data.ApodRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class NetworkModule {

  @Named(API_KEY)
  @Provides
  fun provideAPIKey(): String {
    return "EQaGYIrnEp0wGVeeTF1TOf8CePIhEsTBQ4oj4bPQ"
  }

  @Provides
  fun provideAuthRetrofit(
    @Named(API_KEY) apiKey: String
  ): Retrofit {
    return Retrofit.Builder()
      .baseUrl("https://api.nasa.gov")
      .addConverterFactory(GsonConverterFactory.create())
      .client(
        OkHttpClient.Builder()
          .addInterceptor(Interceptor { chain ->
            val requestWithKey = chain.request().newBuilder()
              .url(
                chain.request()
                  .url.newBuilder()
                  .addQueryParameter("api_key", apiKey)
                  .build()
              )
              .build()
            return@Interceptor chain.proceed(requestWithKey)
          })
          .build()
      )
      .build()
  }

  @Provides
  fun provideApodApi(retrofit: Retrofit): ApodApi {
    return retrofit.create(ApodApi::class.java)
  }

  @Provides
  fun provideApodRepository(
    apodApi: ApodApi,
    apodLocalRepositoryImpl: ApodLocalRepository
  ): ApodRepository {
    return ApodRepositoryImpl(apodApi, apodLocalRepositoryImpl)
  }

  @Provides
  fun provideApodRepositoryLocal(@ApplicationContext context: Context): ApodLocalRepository {
    return ApodLocalRepositoryImpl(context)
  }

  companion object {
    const val API_KEY = "api_key"
  }
}