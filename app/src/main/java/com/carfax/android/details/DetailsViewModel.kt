package com.carfax.android.details

import androidx.lifecycle.ViewModel
import com.carfax.android.api.models.Listing

class DetailsViewModel: ViewModel() {
    lateinit var listing: Listing
}