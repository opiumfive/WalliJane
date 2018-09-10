package com.iterika.walli

import android.Manifest
import android.os.Build
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.iterika.walli.ext.hasPerm
import com.iterika.walli.net.Api
import io.nlopez.smartlocation.SmartLocation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LocationJobService : JobService() {

    var apiWork : Disposable? = null

    override fun onStartJob(job: JobParameters): Boolean {

        val token = Prefs(this).getToken() ?: ""

        val granted = Build.VERSION.SDK_INT < Build.VERSION_CODES.M || (hasPerm(Manifest.permission.ACCESS_FINE_LOCATION) && hasPerm(Manifest.permission.ACCESS_COARSE_LOCATION))

        if (granted) {
            SmartLocation.with(this)
                    .location()
                    .oneFix()
                    .start({
                        Prefs(this).setLat(it.latitude.toString())
                        Prefs(this).setLng(it.longitude.toString())
                        apiWork = Api().service.updateGeo(token, it.latitude.toString(), it.longitude.toString())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({ jobFinished(job, false) },
                                           { jobFinished(job, true)  })
                    })
        }

        return false
    }

    override fun onStopJob(job: JobParameters): Boolean {
        SmartLocation.with(this).location().stop()
        apiWork?.dispose()
        return true
    }
}

