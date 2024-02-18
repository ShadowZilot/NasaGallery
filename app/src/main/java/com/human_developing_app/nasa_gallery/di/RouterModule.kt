package com.human_developing_app.nasa_gallery.di

import androidx.fragment.app.FragmentActivity
import com.human_developing_app.nasa_gallery.base.Router
import com.human_developing_app.nasa_gallery.base.RouterImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

@Module
@InstallIn(ActivityComponent::class)
class RouterModule {

  @Provides
  @Inject
  fun provideRouter(activity: FragmentActivity): Router {
    return RouterImpl(activity)
  }
}