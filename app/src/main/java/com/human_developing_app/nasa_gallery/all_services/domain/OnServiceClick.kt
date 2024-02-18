package com.human_developing_app.nasa_gallery.all_services.domain

import androidx.fragment.app.Fragment

interface OnServiceClick {

  fun onServiceSelect(destination: Class<out Fragment>)
}