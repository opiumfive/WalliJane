package com.iterika.walli.ext

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Patterns
import android.view.View
import android.widget.EditText

fun View.setVisibility(vis: Boolean) {
    this.visibility = if (vis) View.VISIBLE else View.GONE
}

fun View.toggleVisibility() {
    this.visibility = if (this.visibility != View.VISIBLE) View.VISIBLE else View.GONE
}

fun View.enable(en: Boolean) {
    this.isEnabled = en
    this.alpha = if (en) 1.0f else 0.5f
}

fun EditText.onTextChanged(onTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            onTextChanged.invoke(p0.toString())
        }

        override fun afterTextChanged(editable: Editable?) {
        }
    })
}

fun EditText.onJustTextChanged(onTextChanged: () -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            onTextChanged.invoke()
        }

        override fun afterTextChanged(editable: Editable?) {
        }
    })
}

fun EditText.content() = this.text.toString()

fun Context.callPhone(number: String): Boolean {
    try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
        this.startActivity(intent)
        return true
    } catch (e: Exception) {
        e.printStackTrace()
        return false
    }
}