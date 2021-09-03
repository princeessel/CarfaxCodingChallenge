package com.carfax.android.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.carfax.android.api.ApiRepo
import com.carfax.android.api.models.GetCarsResponse
import com.carfax.android.api.models.Listing
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel: ViewModel() {
    fun getListings (): LiveData<List<Listing>?> {
        val liveData = MutableLiveData<List<Listing>?> ()
        ApiRepo.service.getCars()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (object: Observer<GetCarsResponse> {
                override fun onSubscribe(d: Disposable) {
                    // no-op
                }

                override fun onNext(t: GetCarsResponse) {
                    liveData.postValue(t.listings)
                }

                override fun onError(e: Throwable) {
                    liveData.postValue(null)
                }

                override fun onComplete() {
                    // no-op
                }
            })

        return liveData
    }
}