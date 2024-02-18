package com.human_developing_app.nasa_gallery

import com.human_developing_app.nasa_gallery.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): BaseViewModel<Unit>() {

  override fun data(): Flow<Unit> {
    return flowOf(Unit)
  }
}