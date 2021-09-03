package com.carfax.android.details


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import coil.load
import com.carfax.android.LISTING_KEY
import com.carfax.android.R
import com.carfax.android.databinding.ActivityDetailsBinding
import com.carfax.android.utils.launchCallActivity
import com.carfax.android.utils.toPrettyString


class DetailsActivity : AppCompatActivity() {

    private val viewModel by viewModels<DetailsViewModel>()
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(LISTING_KEY)) {
            viewModel.listing = requireNotNull(intent.getParcelableExtra(LISTING_KEY))
        } else {
            finish()
        }

        setupUi()
    }

    private fun setupUi () {
        binding.apply {
            viewModel.listing.let { listing ->
                title.text = "${listing.year} ${listing.make} ${listing.model} ${listing.trim}"
                subtitle.text = "$${listing.currentPrice} | ${listing.mileage?.toPrettyString()} mi"

                photo.load(listing.images?.firstPhoto?.large) {
                    placeholder(R.drawable.car_place_holder)
                    error(R.drawable.car_place_holder)
                }

                locationInfo.value = "${listing.dealer?.address}, ${listing.dealer?.state}"
                exteriorColorInfo.value = listing.exteriorColor
                interiorColorInfo.value = listing.interiorColor
                driveTypeInfo.value = listing.drivetype
                transmissionInfo.value = listing.transmission
                bodyStyleInfo.value = listing.bodytype
                engineInfo.value = listing.engine
                fuelInfo.value = listing.fuel

                callButton.setOnClickListener {
                    launchCallActivity(listing.dealer?.phone)
                }
            }
        }
    }
}