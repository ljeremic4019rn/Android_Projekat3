package com.example.projekat3.data.models.stocks

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DetailedStock(
    val symbol: String,
    val name: String,
    val currency: String,
    val last: Double,
    val open: Double,
    val close: Double,
    val bid: Double,
    val ask: Double,
    val metrics: Metrics,
    val chart: Chart
) : Parcelable
