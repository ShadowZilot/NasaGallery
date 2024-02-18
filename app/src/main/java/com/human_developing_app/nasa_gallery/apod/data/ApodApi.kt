package com.human_developing_app.nasa_gallery.apod.data

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

interface ApodApi {

  @GET("/planetary/apod")
  suspend fun getApod(
    @Query("start_date") startDate: String,
    @Query("end_date") endDate: String,
    @Query("thumbs") thumbs: Boolean = true
  ): List<ApodCloudModel>

  @GET("/planetary/apod")
  suspend fun getCurrentApod(
    @Query("start_date") date: String,
    @Query("thumbs") thumbs: Boolean = true
  ): List<ApodCloudModel>

  @Streaming
  @GET
  suspend fun downloadApodImage(@Url imageUrl: String): Response<ResponseBody>
}