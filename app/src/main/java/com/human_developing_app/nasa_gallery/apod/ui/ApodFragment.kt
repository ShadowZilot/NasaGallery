package com.human_developing_app.nasa_gallery.apod.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.human_developing_app.nasa_gallery.apod.domain.ApodDomainModel
import com.human_developing_app.nasa_gallery.apod.domain.ApodViewModel
import com.human_developing_app.nasa_gallery.base.BaseFragment
import com.human_developing_app.nasa_gallery.base.ErrorDialog
import com.human_developing_app.nasa_gallery.base.Router
import com.human_developing_app.nasa_gallery.databinding.FragmentApodBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject


@AndroidEntryPoint
class ApodFragment : BaseFragment<FragmentApodBinding>(bindingInflater = { inflater, container ->
  FragmentApodBinding.inflate(inflater, container, false)
}), ShareCallback<ApodDomainModel>, DateSelectCallback {
  @Inject
  lateinit var router: Router
  private val viewModel by viewModels<ApodViewModel>()
  private val apodAdapter = ApodAdapter(this, this)
  private val pageChangeCallback = object : OnPageChangeCallback() {
    override fun onPageSelected(position: Int) {
      super.onPageSelected(position)
      viewModel.changeCurrentItem(position)
    }
  }

  override fun onLoading() {
    binding.contentLoading.visibility = View.VISIBLE
  }

  override fun onLoaded() {
    binding.contentLoading.visibility = View.GONE
  }

  override fun onError(errorMessage: String) {
    binding.contentLoading.visibility = View.GONE
    ErrorDialog(requireContext(), errorMessage).show()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    lceCollect(viewModel.lceState())
    binding.apodSlides.adapter = apodAdapter
    binding.apodSlides.registerOnPageChangeCallback(pageChangeCallback)
    (binding.apodSlides[0] as RecyclerView).itemAnimator = null
    viewModel.initApod()
    lifecycleScope.launch {
      viewModel.data().collect { apods ->
        apodAdapter.submitList(apods.apodsList) {
          binding.apodSlides.setCurrentItem(apods.middleIndex, true)
        }
      }
    }
    lifecycleScope.launch {
      viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
        viewModel.shareFlow().collect { shareModel ->
          if (shareModel != null) {
            startActivity(shareModel.shareIntent())
          }
        }
      }
    }
  }

  override fun onDestroyView() {
    binding.apodSlides.adapter = null
    binding.apodSlides.unregisterOnPageChangeCallback(pageChangeCallback)
    super.onDestroyView()
  }

  override fun selectDate(date: Date) {
    val (year, month, dayOfMonth) = viewModel.currentTime(date)
    val datePicker = DatePickerDialog(
      requireContext(), viewModel,
      year, month, dayOfMonth
    )
    datePicker.show()
  }

  override fun onDestroy() {
    Log.d("ApodFragment", "ApodFragment onDestroy invoked")
    super.onDestroy()
  }

  override fun shareItem(item: ApodDomainModel) {
    viewModel.shareApod(item)
  }
}
