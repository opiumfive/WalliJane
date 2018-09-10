package com.iterika.walli.ext

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun  <T> Single<T>.observeAsync(scheduler: Scheduler = Schedulers.io()) = this.subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())

fun  <T> Observable<T>.observeAsync(scheduler: Scheduler = Schedulers.io()) = this.subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())

fun  <T> Flowable<T>.observeAsync(scheduler: Scheduler = Schedulers.io()) = this.subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())

fun Completable.observeAsync(scheduler: Scheduler = Schedulers.io()) = this.subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())