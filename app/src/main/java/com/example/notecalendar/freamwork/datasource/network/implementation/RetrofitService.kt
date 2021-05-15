package com.example.notecalendar.freamwork.datasource.network.implementation

import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET()
    fun getNotes(@Query("gt") startDate : String, @Query("lt") endDate: String) : 
}