package com.human_developing_app.nasa_gallery.apod.domain

import android.content.Intent
import android.net.Uri

data class ApodShareModel(
  val title: String,
  val description: String,
  val image: Uri
) {

  fun shareIntent(): Intent {
    val sendIntent: Intent = Intent().apply {
      action = Intent.ACTION_SEND
      type = "*/*"
      putExtra(Intent.EXTRA_TITLE, title)
      putExtra(Intent.EXTRA_TEXT, "$title\n\n$description")
      putExtra(Intent.EXTRA_STREAM, image)
      flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    }
    return Intent.createChooser(sendIntent, null)
  }
}