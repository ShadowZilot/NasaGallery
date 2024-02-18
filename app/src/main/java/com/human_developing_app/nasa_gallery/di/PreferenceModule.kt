package com.human_developing_app.nasa_gallery.di

import android.content.Context
import android.content.SharedPreferences
import com.human_developing_app.nasa_gallery.base.StringService
import com.human_developing_app.nasa_gallery.base.StringServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class PreferenceModule {

  @Provides
  fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
    return context.getSharedPreferences(DEFAULT_SHARED_PREF_NAME, Context.MODE_PRIVATE)
  }

  @Provides
  fun provideStringService(@ApplicationContext context: Context): StringService {
    return StringServiceImpl(context)
  }

  companion object {
    const val DEFAULT_SHARED_PREF_NAME = "default"
  }
}