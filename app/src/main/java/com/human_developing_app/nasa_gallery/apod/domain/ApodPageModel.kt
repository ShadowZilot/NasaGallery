package com.human_developing_app.nasa_gallery.apod.domain

data class ApodPageModel(
  val apodsList: List<ApodDomainModel> = emptyList(),
  val middleIndex: Int = 0
)