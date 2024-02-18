package com.human_developing_app.nasa_gallery.all_services.ui

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.human_developing_app.nasa_gallery.all_services.domain.NasaServiceModel
import com.human_developing_app.nasa_gallery.all_services.domain.OnServiceClick
import com.human_developing_app.nasa_gallery.databinding.ServiceItemBinding

class ServiceViewHolder(
  private val binding: ServiceItemBinding,
  private val listener: OnServiceClick
) : ViewHolder(binding.root) {

  fun bind(serviceModel: NasaServiceModel) {
    binding.serviceImage.clipToOutline = true
    binding.serviceImage.setImageResource(serviceModel.image)
    binding.serviceImage.contentDescription = serviceModel.title
    binding.serviceDescription.text = serviceModel.description
    binding.serviceName.text = serviceModel.title
    binding.serviceButton.setOnClickListener {
      listener.onServiceSelect(serviceModel.targetFragment)
    }
  }
}