package com.carfax.android.api.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Listing (
    val dealer: Dealer? = null,
    val make: String? = null,
    val model: String? = null,
    val vin: String? = null,
    val mileage: Number? = null,
    val currentPrice: Number? = null,
    val exteriorColor: String? = null,
    val interiorColor: String? = null,
    val engine: String? = null,
    val fuel: String? = null,
    val drivetype: String? = null,
    val transmission: String? = null,
    val bodytype: String? = null,
    val trim: String? = null,
    val images: Images? = null,
    val year: Int? = null
): Parcelable {

    @Parcelize data class Dealer (
        val phone: String? = null,
        val address: String? = null,
        val state: String? = null
    ): Parcelable

    @Parcelize data class Images (val firstPhoto: FirstPhoto? = null): Parcelable {
        @Parcelize data class FirstPhoto (val large: String? = ""): Parcelable
    }
}