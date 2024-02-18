package com.human_developing_app.nasa_gallery.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel<DATA> : ViewModel() {
  protected val lceFlow = MutableSharedFlow<LCEState>(1)

  fun lceState() = lceFlow

  abstract fun data(): Flow<DATA>
}