package com.human_developing_app.nasa_gallery.base

import android.content.Context
import java.lang.ref.WeakReference

class StringServiceImpl(
  context: Context
) : StringService {
  private val resources = WeakReference(context.resources)

  override fun string(id: Int): String {
    return resources.get()?.getString(id) ?: throw StringServiceError()
  }

  override fun string(id: Int, vararg args: Any): String {
    return resources.get()?.getString(id, *args) ?: throw StringServiceError()
  }
}