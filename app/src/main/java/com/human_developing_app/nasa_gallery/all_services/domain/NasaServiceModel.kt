package com.human_developing_app.nasa_gallery.all_services.domain

import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment

data class NasaServiceModel(
  val title: String,
  val description: String,
  @DrawableRes val image: Int,
  val targetFragment: Class<out Fragment>
)