package com.iterika.walli.ext

fun Int.getKBit(k: Int) = this.shr(k).and(1) == 1
