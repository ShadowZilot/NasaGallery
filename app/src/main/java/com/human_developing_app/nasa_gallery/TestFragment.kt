package com.human_developing_app.nasa_gallery

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.human_developing_app.nasa_gallery.base.BaseFragment
import com.human_developing_app.nasa_gallery.databinding.FragmentTestBinding
import kotlin.properties.Delegates

class TestFragment : BaseFragment<FragmentTestBinding>(bindingInflater = { inflater, container ->
  FragmentTestBinding.inflate(inflater, container, false)
}) {
  private var counter by Delegates.observable(0) { property, oldValue, newValue ->
    binding.counter.text = newValue.toString()
    animateProgress(binding.testProgress.progress, newValue)
  }

  override fun onLoading() {}

  override fun onLoaded() {}

  private fun animateProgress(oldValue: Int, newValue: Int) {
//    binding.testProgress.setProgress(newValue, true)

    ObjectAnimator.ofInt(
      binding.testProgress, "progress",
      oldValue, newValue
    ).apply {
      interpolator = DecelerateInterpolator()
      duration = 200
    }.start()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.incrementButton.setOnClickListener {
      if (counter + 10 > 100) {
        counter = 0
      } else {
        counter += 10
      }
    }
  }
}