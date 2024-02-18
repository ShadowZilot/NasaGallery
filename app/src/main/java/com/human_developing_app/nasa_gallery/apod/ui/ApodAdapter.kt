package com.human_developing_app.nasa_gallery.apod.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.human_developing_app.nasa_gallery.apod.domain.ApodDomainModel
import com.human_developing_app.nasa_gallery.databinding.ApodSlideItemBinding

class ApodAdapter(
  private val shareCallback: ShareCallback<ApodDomainModel>,
  private val selectDateCallback: DateSelectCallback
) : ListAdapter<ApodDomainModel, ApodViewHolder>(object : DiffUtil.ItemCallback<ApodDomainModel>() {
  override fun areItemsTheSame(oldItem: ApodDomainModel, newItem: ApodDomainModel): Boolean {
    return oldItem.imageUrl == newItem.imageUrl
  }

  override fun areContentsTheSame(oldItem: ApodDomainModel, newItem: ApodDomainModel): Boolean {
    return (oldItem.title == newItem.title) &&
      (oldItem.dateUi == newItem.dateUi) &&
      (oldItem.imageUrl == newItem.imageUrl) &&
      (oldItem.shareLce == newItem.shareLce)
  }
}) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApodViewHolder {
    return ApodViewHolder(
      ApodSlideItemBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      ),
      shareCallback, selectDateCallback
    )
  }

  override fun onBindViewHolder(holder: ApodViewHolder, position: Int) {
    holder.bind(currentList[position])
  }
}