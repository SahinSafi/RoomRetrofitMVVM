package com.safi.assignment.mainActivity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import android.net.Network
import android.media.Rating

class ShowSearchModel {

    @SerializedName("score")
    @Expose
    private var score: Double? = null

    @SerializedName("show")
    @Expose
    private var show: Show? = null

    fun getScore(): Double? {
        return score
    }

    fun setScore(score: Double?) {
        this.score = score
    }

    fun getShow(): Show? {
        return show
    }

    fun setShow(show: Show?) {
        this.show = show
    }


    class Show {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("url")
        @Expose
        var url: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("type")
        @Expose
        var type: String? = null

        @SerializedName("language")
        @Expose
        var language: String? = null

        @SerializedName("genres")
        @Expose
        var genres: List<String>? = null

        @SerializedName("status")
        @Expose
        var status: String? = null

        @SerializedName("runtime")
        @Expose
        var runtime: Int? = null

        @SerializedName("averageRuntime")
        @Expose
        var averageRuntime: Int? = null

        @SerializedName("premiered")
        @Expose
        var premiered: String? = null

        @SerializedName("ended")
        @Expose
        var ended: String? = null

        @SerializedName("officialSite")
        @Expose
        var officialSite: String? = null

        @SerializedName("rating")
        @Expose
        var rating: Rating? = null

        @SerializedName("weight")
        @Expose
        var weight: Int? = null

        @SerializedName("network")
        @Expose
        var network: Network? = null

        @SerializedName("image")
        @Expose
        var image: Image? = null

        @SerializedName("webChannel")
        @Expose
        var webChannel: Any? = null

        @SerializedName("dvdCountry")
        @Expose
        var dvdCountry: Any? = null

        @SerializedName("summary")
        @Expose
        var summary: String? = null

        @SerializedName("updated")
        @Expose
        var updated: Int? = null

    }

    class Image {
        @SerializedName("medium")
        @Expose
        var medium: String? = null

        @SerializedName("original")
        @Expose
        var original: String? = null
    }

}