package com.human_developing_app.nasa_gallery.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseFragment<VIEW : ViewBinding>(
  private val bindingInflater: (inflater: LayoutInflater, container: ViewGroup?) -> VIEW
) : Fragment() {
  private var _binding: VIEW? = null
  protected val binding get() = _binding!!

  abstract fun onLoading()

  open fun onError(errorMessage: String) {
    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
  }

  abstract fun onLoaded()

  fun lceCollect(lceFlow: Flow<LCEState>) {
    lifecycleScope.launch {
      viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
        lceFlow.collect { lce ->
          if (lce.isLoading) onLoading() else onLoaded()
          if (lce.isError) onError(lce.errorMessage)
        }
      }
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = bindingInflater.invoke(inflater, container)
    return binding.root
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}