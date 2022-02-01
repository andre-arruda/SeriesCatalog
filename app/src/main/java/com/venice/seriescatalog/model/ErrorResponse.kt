package com.venice.seriescatalog.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.io.IOException

/*
 * Created by Andre Arruda on 2/1/22.
 */
@Parcelize
data class ErrorResponse(
    @Json(name = "message") override val message: String? = null,
    @Json(name = "status") val status: Long? = null,
    @Json(name = "code") val code: Int? = null,
) :  IOException(message), Parcelable
