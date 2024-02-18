package com.human_developing_app.nasa_gallery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.human_developing_app.nasa_gallery.all_services.ui.ServiceFragment
import com.human_developing_app.nasa_gallery.base.Router
import com.human_developing_app.nasa_gallery.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  private var _binding: ActivityMainBinding? = null
  private val binding get() = _binding!!
  @Inject
  lateinit var router: Router

  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen()
    super.onCreate(savedInstanceState)
    _binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    if (savedInstanceState == null) router.navigateWithoutBack(
      ServiceFragment::class.java,
      ServiceFragment.TAG
    )
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }
}