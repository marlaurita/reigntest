package com.reign.reigntest.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.reign.reigntest.model.*
import kotlinx.coroutines.*
import retrofit2.Response
import java.lang.Exception

class ListViewModel (application: Application) : AndroidViewModel(application){
    val reportService = ReportService.getReportService()
    var job: Job? = null
    val reports = MutableLiveData<List<Report>>()
    val loading = MutableLiveData<Boolean>()
    val reportLoadError = MutableLiveData<String?>()
    private var repository:ReportRepository = ReportRepository(application)
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
        
    }

    fun refresh() {
        fetchReports()
    }

    fun deleteReportDatabase(report: Report) {
        repository.deleteReport(report)
    }

    private fun fetchReports() {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response : Response<ResponseHits> = reportService.getReports()
            withContext(Dispatchers.Main){
                if (response.isSuccessful) {
                    reports.value = response.body()?.hits
                    reports.value?.toList()?.let { repository.setReport(it) }
                } else {
                    onError(response.message())
                }
            }
        }
        loading.value = false
    }

    private fun onError(message: String) {
        reportLoadError.postValue("")
        loading.postValue(false)
        try {
            reports.postValue(repository.getReports())
        } catch (e: Exception){
            Log.e("TAG", e.localizedMessage)
        }
        Log.e("LIST VIEW MODEL", message)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}