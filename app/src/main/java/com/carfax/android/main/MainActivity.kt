package com.carfax.android.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.carfax.android.LISTING_KEY
import com.carfax.android.R
import com.carfax.android.api.models.Listing
import com.carfax.android.databinding.ActivityMainBinding
import com.carfax.android.databinding.ItemViewListingsListBinding
import com.carfax.android.details.DetailsActivity
import com.carfax.android.utils.launchCallActivity
import com.carfax.android.utils.toPrettyString

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel> ()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadListings ()
    }

    private fun loadListings () {
        viewModel.getListings().observe(this) { listings ->
            if (listings == null) {
                Toast.makeText(this@MainActivity, "There was an error loading the listings.", Toast.LENGTH_LONG).show()
            } else {
                binding.recyclerview.adapter = Adapter(listings) { listing, initiateCall ->
                    if (initiateCall) {
                        launchCallActivity(listing.dealer?.phone)
                    } else {
                        val intent = Intent (this@MainActivity, DetailsActivity::class.java)
                        intent.putExtra(LISTING_KEY, listing)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}

private class Adapter (private val listings: List<Listing>, private val callback: (Listing, Boolean) -> Unit): RecyclerView.Adapter<ViewHolder> () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemViewListingsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listing = listings[position]
        holder.binding.apply {
            title.text = "${listing.year} ${listing.make} ${listing.model} ${listing.trim}"
            subtitle.text = "$${listing.currentPrice} | ${listing.mileage?.toPrettyString()} mi"

            photo.load(listing.images?.firstPhoto?.large) {
                placeholder(R.drawable.car_place_holder)
                error(R.drawable.car_place_holder)
            }

            location.text = "${listing.dealer?.address}, ${listing.dealer?.state}"
        }

        holder.binding.callButton.setOnClickListener {
            callback (listing, true)
        }

        holder.binding.root.setOnClickListener {
            callback (listing, false)
        }
    }

    override fun getItemCount() = listings.size
}

private class ViewHolder(val binding: ItemViewListingsListBinding): RecyclerView.ViewHolder (binding.root)