package com.safi.assignment.mainActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saifurs.user.network.ApiClient
import com.saifurs.user.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val apiInterface: ApiInterface =
        ApiClient().getClient.create(ApiInterface::class.java)

    val onProgress = MutableLiveData<Boolean>()
    val onSuccess = MutableLiveData<List<ShowSearchModel>>()
    val onFailed = MutableLiveData<String>()

    fun search(search: String) {
        onProgress.postValue(true)
        val call: Call<List<ShowSearchModel>> = apiInterface.searchShow(search)
        call.enqueue(object : Callback<List<ShowSearchModel>> {
            override fun onResponse(
                call: Call<List<ShowSearchModel>>,
                response: Response<List<ShowSearchModel>>
            ) {
                if (response.code() == 200) {
                    onSuccess.postValue(response.body())
                } else {
                    onFailed.postValue(response.message())
                }
                onProgress.postValue(false)
            }

            override fun onFailure(call: Call<List<ShowSearchModel>>, t: Throwable) {
                onProgress.postValue(false)
                onFailed.postValue(t.message)
            }

        })
    }
}