package com.venice.seriescatalog.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/*
 * Created by Andre Arruda on 1/28/22.
 */
@Parcelize
data class ImageUrl(
        @Json(name = "medium") val medium: String?,
        @Json(name = "original") val original: String?,
): Parcelable
