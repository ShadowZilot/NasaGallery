package com.human_developing_app.nasa_gallery.apod.ui

import android.graphics.Paint
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.human_developing_app.nasa_gallery.R
import com.human_developing_app.nasa_gallery.apod.domain.ApodDomainModel
import com.human_developing_app.nasa_gallery.databinding.ApodSlideItemBinding

class ApodViewHolder(
  private val binding: ApodSlideItemBinding,
  private val shareCallback: ShareCallback<ApodDomainModel>,
  private val dateCallback: DateSelectCallback
) : ViewHolder(binding.root) {
  private val shimmer = Shimmer.AlphaHighlightBuilder()
    .setDuration(1800)
    .setBaseAlpha(0.7f)
    .setHighlightAlpha(0.6f)
    .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
    .setAutoStart(true)
    .build()
  private val shimmerDrawable = ShimmerDrawable().apply {
    setShimmer(shimmer)
  }

  fun bind(apodDomainModel: ApodDomainModel) {
    binding.apply {
      currentDate.paintFlags = binding.currentDate.paintFlags or Paint.UNDERLINE_TEXT_FLAG
      binding.shareButton.setOnClickListener {
        shareCallback.shareItem(apodDomainModel)
      }
      binding.currentDate.setOnClickListener {
        dateCallback.selectDate(apodDomainModel.date)
      }
      apodTitle.text = apodDomainModel.title
      apodDesc.text = apodDomainModel.description
      Glide.with(binding.apodImage)
        .load(apodDomainModel.imageUrl)
        .placeholder(shimmerDrawable)
        .into(binding.apodImage)
      currentDate.text = apodDomainModel.dateUi
    }
    when {
      apodDomainModel.shareLce.isLoading -> {
        binding.shareButton.text = ""
        binding.shareLoading.visibility = View.VISIBLE
      }

      apodDomainModel.shareLce.isError -> {
        binding.shareButton.setText(R.string.share_label)
        binding.shareLoading.visibility = View.GONE
        Toast.makeText(
          binding.root.context,
          apodDomainModel.shareLce.errorMessage, Toast.LENGTH_SHORT
        ).show()
      }

      !apodDomainModel.shareLce.isLoading -> {
        binding.shareButton.setText(R.string.share_label)
        binding.shareLoading.visibility = View.GONE
      }

    }
  }
}