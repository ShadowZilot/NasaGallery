package com.human_developing_app.nasa_gallery.base

import android.app.AlertDialog
import android.content.Context
import com.human_developing_app.nasa_gallery.R

class ErrorDialog(
  private val context: Context,
  private val message: String
) {

  fun show() {
    AlertDialog.Builder(context)
      .setTitle(R.string.error_title_dialog)
      .setMessage(message)
      .setPositiveButton(
        android.R.string.ok
      ) { _, _ -> }
      .create()
      .show()
  }
}