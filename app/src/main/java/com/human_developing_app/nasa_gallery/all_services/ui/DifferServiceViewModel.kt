package com.human_developing_app.nasa_gallery.all_services.ui

import androidx.recyclerview.widget.DiffUtil
import com.human_developing_app.nasa_gallery.all_services.domain.NasaServiceModel

class DifferServiceViewModel : DiffUtil.ItemCallback<NasaServiceModel>() {

  override fun areItemsTheSame(oldItem: NasaServiceModel, newItem: NasaServiceModel): Boolean {
    return oldItem == newItem
  }

  override fun areContentsTheSame(oldItem: NasaServiceModel, newItem: NasaServiceModel): Boolean {
    return oldItem == newItem
  }
}