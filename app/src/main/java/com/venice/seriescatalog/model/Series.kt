package com.venice.seriescatalog.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/*
 * Created by Andre Arruda on 1/28/22.
 */
@Parcelize
data class Series(
    @Json(name = "score" ) val score: Double,
    @Json(name = "show") val show: Show
): Parcelable


@Parcelize
data class Show(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "image") val image: ImageUrl?,
    @Json(name = "genres") val genres: List<String>,
    @Json(name = "summary") val summary: String?,
    @Json(name = "schedule") val schedule: Schedule,
    @Json(name = "runtime") val runtime: Int,
    val episodes: List<Episode>?
): Parcelable

@Parcelize
data class Schedule(
        @Json(name = "time") val time: String,
        @Json(name = "days") val days: List<String>
): Parcelable