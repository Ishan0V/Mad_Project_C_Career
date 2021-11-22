package com.ivpy.career.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Mentor(
    var name:String="",
    var degree :String="",
    var experience :Int= 0,
    var institute : String="",
    var image :String="",
    var id: String="",
    var mentor_id:String=""
): Parcelable
