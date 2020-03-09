package com.reign.reigntest.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ReportService {
    private val BASE_URL = "https://hn.algolia.com/api/v1/"
    fun getReportService () : ReportApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ReportApi::class.java)
    }
}