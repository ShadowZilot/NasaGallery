package com.human_developing_app.nasa_gallery.apod.domain

import android.app.DatePickerDialog.OnDateSetListener
import android.widget.DatePicker
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.human_developing_app.nasa_gallery.R
import com.human_developing_app.nasa_gallery.apod.data.ApodCloudModel
import com.human_developing_app.nasa_gallery.apod.data.ApodImageDownloadException
import com.human_developing_app.nasa_gallery.apod.data.ApodRepository
import com.human_developing_app.nasa_gallery.apod.data.toApodDate
import com.human_developing_app.nasa_gallery.base.BaseViewModel
import com.human_developing_app.nasa_gallery.base.LCEState
import com.human_developing_app.nasa_gallery.base.StringService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ApodViewModel @Inject constructor(
  private val strings: StringService,
  private val apodRepository: ApodRepository
) : BaseViewModel<ApodPageModel>(), OnDateSetListener {
  private val currentDate = Date()
  private val apodFlow = MutableStateFlow(ApodPageModel())
  private val apodShareFlow = MutableStateFlow<ApodShareModel?>(null)
  private var currentItem: Int = 0

  fun initApod() {
    viewModelScope.launch {
      emitCurrentApod { apodRepository.getCurrentApod() }
    }
  }

  override fun data(): Flow<ApodPageModel> {
    return apodFlow
  }

  fun shareFlow() = apodShareFlow

  private suspend fun emitShareLceToApod(apod: ApodDomainModel, shareLCEState: LCEState) {
    val currentApodList = apodFlow.value.apodsList.toMutableList()
    val apodShare = currentApodList.find { findApod ->
      findApod.dateUi == apod.dateUi
    }!!
    val index = currentApodList.indexOf(apodShare)
    currentApodList[index] = apodShare.changeShareLceState(shareLCEState)
    apodFlow.emit(ApodPageModel(currentApodList, currentItem))
  }

  fun shareApod(apod: ApodDomainModel) {
    viewModelScope.launch(Dispatchers.IO) {
      emitShareLceToApod(apod, LCEState(isLoading = true, isError = false))
      try {
        val shareModel = apod.toShareModel(
          apodRepository.getApodFileUri(apod.imageUrl)
        )
        apodShareFlow.emit(shareModel)
        emitShareLceToApod(apod, LCEState(isLoading = false, isError = false))
      } catch (e: ApodImageDownloadException) {
        emitShareLceToApod(
          apod,
          LCEState(
            isLoading = false, isError = true,
            strings.string(R.string.download_apod_error)
          )
        )
      } catch (e: IOException) {
        emitShareLceToApod(
          apod,
          LCEState(
            isLoading = false, isError = true,
            strings.string(R.string.no_internet)
          )
        )
      } finally {
        delay(200)
        apodShareFlow.emit(null)
      }
    }
  }

  private suspend fun emitCurrentApod(apodLambda: suspend () -> List<ApodCloudModel>) {
    lceFlow.emit(LCEState(isLoading = true, isError = false))
    try {
      val cloudApod = apodLambda.invoke()
      val targetList = cloudApod.map { cloudApodItem ->
        ApodDomainModel(cloudApodItem) { date ->
          dateLabel(date)
        }
      }
      currentItem = cloudApod.indexOf(cloudApod.find { apod ->
          apod.date == currentDate.toApodDate()
      })
      apodFlow.emit(ApodPageModel(targetList, currentItem))
      lceFlow.emit(LCEState(isLoading = false, isError = false))
    } catch (error: HttpException) {
      val errorBody = JSONObject(error.response()?.errorBody()?.string() ?: "")
      lceFlow.emit(
        LCEState(
          isLoading = false, isError = true,
          errorBody.getString("msg")
        )
      )
    } catch (error: UnknownHostException) {
      lceFlow.emit(
        LCEState(
          isLoading = false, isError = true,
          strings.string(R.string.no_internet)
        )
      )
    }
  }

  private fun dateLabel(date: Date): String = strings.string(
    R.string.date_label,
    SimpleDateFormat(
      strings.string(R.string.date_pattern),
      Locale.getDefault()
    ).format(date)
  )

  fun changeCurrentItem(currentItem: Int) {
    this.currentItem = currentItem
  }

  fun currentTime(date: Date): Triple<Int, Int, Int> {
    val calendar = GregorianCalendar()
    calendar.timeInMillis = date.time
    return Triple(
      calendar.get(Calendar.YEAR),
      calendar.get(Calendar.MONTH),
      calendar.get(Calendar.DAY_OF_MONTH)
    )
  }

  override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
    val calendar = GregorianCalendar()
    calendar.timeInMillis = 0L
    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, month)
    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
    currentDate.time = calendar.timeInMillis
    viewModelScope.launch {
      emitCurrentApod { apodRepository.getApod(currentDate) }
    }
  }
}