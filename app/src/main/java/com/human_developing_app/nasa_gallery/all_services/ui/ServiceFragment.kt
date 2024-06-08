package com.human_developing_app.nasa_gallery.all_services.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.human_developing_app.nasa_gallery.all_services.domain.OnServiceClick
import com.human_developing_app.nasa_gallery.all_services.domain.ServicesViewModel
import com.human_developing_app.nasa_gallery.base.BaseFragment
import com.human_developing_app.nasa_gallery.base.Router
import com.human_developing_app.nasa_gallery.databinding.FragmentServicesOverviewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ServiceFragment :
  BaseFragment<FragmentServicesOverviewBinding>(bindingInflater = { inflater, container ->
    FragmentServicesOverviewBinding.inflate(inflater, container, false)
  }), OnServiceClick {
  private val viewModel by viewModels<ServicesViewModel>()

  @Inject
  lateinit var router: Router

  override fun onLoading() {}

  override fun onLoaded() {}

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.overviewList.layoutManager = LinearLayoutManager(requireContext())
    val adapter = ServicesAdapter(this)
    binding.overviewList.adapter = adapter
    viewLifecycleOwner.lifecycleScope.launch {
      viewModel.data().collect { models ->
        adapter.submitList(models)
      }
    }
  }

  override fun onServiceSelect(destination: Class<out Fragment>) {
    router.navigate(destination, TARGET_SERVICE)
  }

  companion object {
    const val TAG = "ServicesFragment"
    const val TARGET_SERVICE = "TargetService"
  }
}