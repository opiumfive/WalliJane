package com.iterika.walli.model

data class MessageDataShort(val status: Int?, val result: MessageResultShort?)

data class MessageResultShort(val error: String?, val message: String?)
