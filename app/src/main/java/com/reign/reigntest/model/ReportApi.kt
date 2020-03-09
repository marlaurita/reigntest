package com.reign.reigntest.model
import retrofit2.Response
import retrofit2.http.GET

interface ReportApi {
    @GET ("search_by_date?query=android")
    suspend fun getReports(): Response<ResponseHits>
}