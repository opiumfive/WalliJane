package com.iterika.walli.model

data class Login(val status: Int?, val result: Result?)

data class Result(val data: Datum?)

data class Datum(val error: String?, val token: String?)