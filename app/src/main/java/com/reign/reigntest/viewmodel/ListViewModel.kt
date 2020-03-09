package com.reign.reigntest.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reign.reigntest.model.ReportService
import com.reign.reigntest.model.Report
import com.reign.reigntest.model.ResponseHits
import kotlinx.coroutines.*
import retrofit2.Response

class ListViewModel : ViewModel() {
    val reportService = ReportService.getReportService()
    var job: Job? = null
    val news = MutableLiveData<List<Report>>()
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
        
    }

    fun refresh() {
        fetchReports()
    }

    private fun fetchReports() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response : Response<ResponseHits> = reportService.getReports()
            withContext(Dispatchers.Main){
                if (response.isSuccessful) {
                    news.value = response.body()?.hits
                }
            }
        }
    }

    private fun onError(message: String) {
        Log.e("LIST VIEW MODEL", message)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}