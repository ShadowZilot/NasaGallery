package com.human_developing_app.nasa_gallery.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN
import com.human_developing_app.nasa_gallery.R

class RouterImpl(
  activity: FragmentActivity
) : Router {
  private val fragmentManager = activity.supportFragmentManager

  override fun popBackStack() {
    fragmentManager.popBackStack()
  }

  override fun navigate(fragment: Class<out Fragment>, tag: String, args: Bundle) {
    fragmentManager
      .beginTransaction()
      .setTransition(TRANSIT_FRAGMENT_OPEN)
      .replace(R.id.mainNavHost, fragment, args, tag)
      .addToBackStack(tag)
      .commit()
  }

  override fun navigateWithoutBack(fragment: Class<out Fragment>, tag: String, args: Bundle) {
    fragmentManager
      .beginTransaction()
      .setTransition(TRANSIT_FRAGMENT_OPEN)
      .replace(R.id.mainNavHost, fragment, args, tag)
      .commit()
  }
}