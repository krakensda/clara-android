package com.pens.it.d4b2018.clara_android.mvvm.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Asset(

        @Expose
        @SerializedName("_id")
        val id: String,

        @Expose
        @SerializedName("name")
        val name: String,

        @Expose
        @SerializedName("image")
        val image: String,

        @Expose
        @SerializedName("quantity")
        val quantity: Int,

        @Expose
        @SerializedName("available")
        val available: Int
)