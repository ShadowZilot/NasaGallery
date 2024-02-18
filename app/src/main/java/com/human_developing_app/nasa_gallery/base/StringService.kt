package com.human_developing_app.nasa_gallery.base

import androidx.annotation.StringRes

interface StringService {

  fun string(@StringRes id: Int): String

  fun string(@StringRes id: Int, vararg args: Any): String
}