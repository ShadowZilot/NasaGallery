package com.human_developing_app.nasa_gallery.all_services.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.human_developing_app.nasa_gallery.all_services.domain.NasaServiceModel
import com.human_developing_app.nasa_gallery.all_services.domain.OnServiceClick
import com.human_developing_app.nasa_gallery.databinding.ServiceItemBinding
import java.lang.ref.WeakReference

class ServicesAdapter(
  private val listener: OnServiceClick
) : ListAdapter<NasaServiceModel, ServiceViewHolder>(
  DifferServiceViewModel()
) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
    return ServiceViewHolder(
      ServiceItemBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      ),
      listener
    )
  }

  override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
    holder.bind(currentList[position])
  }
}