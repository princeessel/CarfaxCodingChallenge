package com.carfax.android.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri

fun Activity.launchCallActivity (phone: String?) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$phone")
    startActivity(intent)
}