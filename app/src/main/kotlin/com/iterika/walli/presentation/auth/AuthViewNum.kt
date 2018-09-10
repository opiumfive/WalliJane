package com.iterika.walli.presentation.auth

object AuthViewNum {
    val BIT = 1
    // bit masks according to views
    //val WALLI_ICON      = BIT.shl(0)
    val SIGNUP_BUTTON   = BIT.shl(0)
    val LOGIN_BUTTON    = BIT.shl(1)
    val LOGIN_EDIT      = BIT.shl(2)
    val PASS_EDIT       = BIT.shl(3)
    val CONFIRM_EDIT    = BIT.shl(4)
    val BACK            = BIT.shl(5)
    val RECOVER         = BIT.shl(6)
    val MIDDLE_BUT      = BIT.shl(7)
    val BOTTOM_BUT      = BIT.shl(8)
}