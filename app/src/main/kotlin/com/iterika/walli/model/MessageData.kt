package com.iterika.walli.model

data class MessageData(val status: Int?, val result: MessageResult?)

data class MessageResult(val data: MessageDatum?)

data class MessageDatum(val error: String?, val message: String?)