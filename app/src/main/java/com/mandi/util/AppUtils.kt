package com.mandi.util

import java.text.NumberFormat
import java.util.*

val numberFormat by lazy {
    NumberFormat.getInstance(Locale("en", "IN")).apply {
        maximumFractionDigits = 2
    }
}