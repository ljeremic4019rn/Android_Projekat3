package com.example.projekat3.data.models.stocks

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Chart (
    val bars: List<Bar>
) : Parcelable