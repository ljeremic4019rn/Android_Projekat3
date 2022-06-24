package com.example.projekat3.data.models.stocks

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Metrics (
    val alpha: Double,
    val beta: Double,
    val sharp: Double,
    val marketCup: Double,
    val eps: Double,
    val ebit: Double
) : Parcelable