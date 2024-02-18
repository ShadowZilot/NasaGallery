package com.human_developing_app.nasa_gallery.base

import android.os.Bundle
import androidx.fragment.app.Fragment

interface Router {

  fun popBackStack()

  fun navigate(
    fragment: Class<out Fragment>, tag: String,
    args: Bundle = Bundle()
  )

  fun navigateWithoutBack(
    fragment: Class<out Fragment>, tag: String,
    args: Bundle = Bundle()
  )
}