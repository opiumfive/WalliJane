package com.iterika.walli.presentation.profile

import com.iterika.walli.domain.LocationInteractor
import com.iterika.walli.domain.ProfileInteractor
import com.iterika.walli.ext.observeAsync
import com.iterika.walli.model.Workday
import com.iterika.walli.presentation.BasePresenter
import com.iterika.walli.presentation.Navigation
import com.iterika.walli.presentation.Screens
import javax.inject.Inject

class ProfilePresenter @Inject constructor() : BasePresenter<IProfileView>() {

    @Inject lateinit var navigation: Navigation
    @Inject lateinit var interactor: ProfileInteractor
    @Inject lateinit var locationInteractor: LocationInteractor

    var wDayState = WorkdayState.NOT_STARTED

    var canStart = false

    fun onStartEndDay() {
        when(wDayState) {
            WorkdayState.STARTED, WorkdayState.PAUSED   ->  {
                interactor.stop()
                        .observeAsync()
                        .subscribe(
                                {
                                    if (it.result?.data?.message != null) {
                                        setState(WorkdayState.NOT_STARTED)
                                    } else {
                                        view?.showMessage(it.result?.data?.error ?: "Error")
                                    }
                                },
                                { view?.showMessage("Network error") }
                        )
                        .connect()
            }
            WorkdayState.NOT_STARTED                    ->  {
                if (canStart) {
                    interactor.start()
                            .observeAsync()
                            .subscribe(
                                    {
                                        if (it.result?.data?.message != null) {
                                            setState(WorkdayState.STARTED)
                                        } else {
                                            view?.showMessage(it.result?.data?.error ?: "Error")
                                        }
                                    },
                                    { view?.showMessage("Network error") }
                            )
                            .connect()
                } else {
                    view?.showMessage("Session not started")
                }
            }
        }

    }

    fun onReady() {

        locationInteractor.initiateLocationUpdate()
                .observeAsync()
                .subscribe({ locationInteractor.setLastLocation(it) })
                .connect()

        interactor.getUserData()
                .observeAsync()
                .subscribe(
                        {
                            if (it.result?.data?.profile != null) {
                                view?.showProfile(it.result.data.profile)
                                when (it.result.data.profile.statusId) {
                                    "A" -> {
                                        canStart = true
                                        setState(WorkdayState.STARTED)
                                    }
                                    "P" -> {
                                        canStart = true
                                        setState(WorkdayState.PAUSED)
                                    }
                                    else -> {
                                        canStart = false
                                        setState(WorkdayState.NOT_STARTED)
                                    }

                                }
                            } else {
                                view?.showMessage(it.result?.data?.error ?: "Error")
                            }
                        },
                        { view?.showMessage("Network error") }
                )
                .connect()

    }

    fun onBreakContinue() {
        when(wDayState) {
            WorkdayState.STARTED -> {
                interactor.stop()
                        .observeAsync()
                        .subscribe(
                                {
                                    if (it.result?.data?.message != null) {
                                        setState(WorkdayState.PAUSED)
                                    } else {
                                        view?.showMessage(it.result?.data?.error ?: "Error")
                                    }
                                },
                                { view?.showMessage("Network error") }
                        )
                        .connect()
            }
            WorkdayState.PAUSED -> {
                interactor.start()
                        .observeAsync()
                        .subscribe(
                                {
                                    if (it.result?.data?.message != null) {
                                        setState(WorkdayState.STARTED)
                                    } else {
                                        view?.showMessage(it.result?.data?.error ?: "Error")
                                    }
                                },
                                { view?.showMessage("Network error") }
                        )
                        .connect()
            }
            else -> Unit
        }
    }

    fun setState(state: WorkdayState) {
        wDayState = state
        when (wDayState) {
            WorkdayState.STARTED     -> {
                view?.setStarted()
                view?.showTabs(true)
            }
            WorkdayState.NOT_STARTED -> {
                view?.setNotStarted()
                view?.showTabs(false)
            }
            WorkdayState.PAUSED      -> {
                view?.setPaused()
                view?.showTabs(true)
            }
        }
    }
}