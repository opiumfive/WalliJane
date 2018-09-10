package com.iterika.walli.repos

import com.iterika.walli.model.Faq
import com.iterika.walli.net.Api
import io.reactivex.Single
import javax.inject.Inject

class FaqRepository @Inject constructor(val api: Api) {

    fun getFaqs(): Single<List<Faq>> = Single.just(listOf(Faq("1", "1"), Faq("2", "2")))
}
