package com.saifurs.user.network

import com.safi.assignment.mainActivity.ShowSearchModel
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("search/shows")
    fun searchShow(@Query("q") search: String):Call<List<ShowSearchModel>>

}