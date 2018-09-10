package com.iterika.walli.ext

import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat

fun Context.hasPerm(perm: String) = ActivityCompat.checkSelfPermission(this, perm) == PackageManager.PERMISSION_GRANTED
