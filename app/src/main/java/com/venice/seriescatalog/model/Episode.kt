package com.venice.seriescatalog.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


/*
 * Created by Andre Arruda on 1/28/22.
 */
@Parcelize
data class Episode(
        @Json(name = "id") val id: Int,
        @Json(name = "image") val image: ImageUrl,
        @Json(name = "name") val name: String,
        @Json(name = "number") val number: String,
        @Json(name = "season") val season: String,
        @Json(name = "summary") val summary: String,
): Parcelable
