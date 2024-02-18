package com.human_developing_app.nasa_gallery.base

data class LCEState(
  val isLoading: Boolean,
  val isError: Boolean,
  val errorMessage: String = ""
)