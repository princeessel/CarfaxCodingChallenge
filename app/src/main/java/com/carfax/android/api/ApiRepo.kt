package com.carfax.android.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.net.InetAddress
import java.util.concurrent.TimeUnit

import com.ncornette.cache.OkCacheControl

object ApiRepo {
    private const val BACKEND_BASE_URL = "https://carfax-for-consumers.firebaseio.com/"

    lateinit var service: WebService

    fun init (context: Context) {
        service = RetrofitFactory()
                    .buildRetrofitInstance(context)
                    .create(WebService::class.java)
    }

    private class RetrofitFactory {
        private lateinit var retrofit: Retrofit

        fun buildRetrofitInstance(context: Context): Retrofit {
            val cacheSize = 10485760L // 10MB cache size
            val cache = Cache(context.cacheDir, cacheSize)

            val okClient = OkCacheControl.on(OkHttpClient.Builder())
                .overrideServerCachePolicy(30, TimeUnit.MINUTES)
                .forceCacheWhenOffline { isInternetAvailable() }
                .apply()
                .cache(cache)
                .build()

            if (!::retrofit.isInitialized) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BACKEND_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(okClient)
                    .build()
            }
            return retrofit
        }
    }

    fun isInternetAvailable(): Boolean {
        return try {
            val ipAdd: InetAddress = InetAddress.getByName("google.com")
            //You can replace it with your name
            !ipAdd.equals("")
        } catch (e: Exception) {
            false
        }
    }
}