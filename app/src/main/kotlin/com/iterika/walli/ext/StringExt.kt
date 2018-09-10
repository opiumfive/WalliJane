package com.iterika.walli.ext

import android.util.Patterns
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

fun String.isEmailValid() = Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.reformatDate() : String {
    val c = Calendar.getInstance()

    try {
        c.time = SimpleDateFormat("MM/dd/yyyy HH:mm:ss aaa", Locale.ENGLISH).parse(this);
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return SimpleDateFormat("MMMM d HH:mm", Locale.ENGLISH).format(c.getTime())
}
