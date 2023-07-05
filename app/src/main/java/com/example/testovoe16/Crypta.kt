package com.example.testovoe16

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Crypta(
    @SerializedName("id") val id: Int,
    @SerializedName("crypto_image") val crypto_image: String,
    @SerializedName("title") val title: String,
    @SerializedName("crypto") val crypto: String,
    @SerializedName("graf") val graf: String,
    @SerializedName("dollar") val dollar: String,
    @SerializedName("cost") var cost: Float,
    @SerializedName("hour") val hour: String,
    @SerializedName("day") val day: String,
    @SerializedName("week") val week: String,
    @SerializedName("mc") val mc: String,
    @SerializedName("vol") val vol: String,
    @SerializedName("cs") val cs: String,
    @SerializedName("add") var add: Boolean
) : java.io.Serializable