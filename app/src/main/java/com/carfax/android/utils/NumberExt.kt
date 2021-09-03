package com.carfax.android.utils

import kotlin.math.ln
import kotlin.math.pow

fun Number.toPrettyString (): String {
    if (this.toDouble() < 1000) return "" + this
    val exp = (ln(this.toDouble()) / ln(1000.0)).toInt()
    return String.format("%.1f %c", this.toDouble() / 1000.0.pow(exp.toDouble()), "kMGTPE"[exp - 1])
}