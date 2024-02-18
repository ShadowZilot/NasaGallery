package com.human_developing_app.nasa_gallery.all_services.domain

import com.human_developing_app.nasa_gallery.R
import com.human_developing_app.nasa_gallery.apod.ui.ApodFragment
import com.human_developing_app.nasa_gallery.base.BaseViewModel
import com.human_developing_app.nasa_gallery.base.StringService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ServicesViewModel @Inject constructor(
  private val strings: StringService
) : BaseViewModel<List<NasaServiceModel>>() {
  private val nasaServiceModels = MutableStateFlow(
    listOf(
      NasaServiceModel(
        strings.string(R.string.apod_service_name),
        strings.string(R.string.apod_service_desc),
        R.drawable.apod_service_image,
        ApodFragment::class.java
      ),
      NasaServiceModel(
        strings.string(R.string.mars_service_name),
        strings.string(R.string.mars_service_desc),
        R.drawable.mars_service_image,
        ApodFragment::class.java
      ),
      NasaServiceModel(
        strings.string(R.string.epic_service_name),
        strings.string(R.string.epic_service_desc),
        R.drawable.epic_service_image,
        ApodFragment::class.java
      )
    )
  )

  override fun data(): Flow<List<NasaServiceModel>> {
    return nasaServiceModels
  }
}