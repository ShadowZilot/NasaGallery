package com.human_developing_app.nasa_gallery.apod.ui

interface ShareCallback<T> {

  fun shareItem(item: T)
}