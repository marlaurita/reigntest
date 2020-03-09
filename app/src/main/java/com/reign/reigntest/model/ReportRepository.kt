package com.reign.reigntest.model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ReportRepository(application: Application) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var reportDAO: ReportDAO?

    init {
        val db = AppDatabase.getAppDataBase(application)
        reportDAO = db?.reportDao()
    }

    fun getReports() = reportDAO?.getAll()

    fun setReport(report: List<Report>) {
        launch  { setReportBG(report) }
    }

    private suspend fun setReportBG(report: List<Report>){
        withContext(Dispatchers.IO){
            reportDAO?.insertAll(*report.toTypedArray())
        }
    }

}