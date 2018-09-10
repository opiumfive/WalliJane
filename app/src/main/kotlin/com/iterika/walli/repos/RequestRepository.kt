package com.iterika.walli.repos

import com.iterika.walli.Prefs
import com.iterika.walli.model.RequestCategory
import com.iterika.walli.net.Api
import io.reactivex.Single
import javax.inject.Inject

class RequestRepository @Inject constructor(val api: Api, val authRepository: AuthRepository, val prefs: Prefs) {

    fun getRequests() = api.service.requests(authRepository.getToken().blockingGet()!!)

    fun getFilterCats() = Single.just(
            listOf(
                    RequestCategory("0", "All", getFilter().equals("All")),
                    RequestCategory("1", "New", getFilter().equals("New")),
                    RequestCategory("2", "Accepted", getFilter().equals("Accepted")),
                    RequestCategory("3", "Express", getFilter().equals("Express")),
                    RequestCategory("4", "Standart", getFilter().equals("Standart"))
            )
    )

    fun getFilter() = prefs.getFilter()

    fun setFilter(requestCategory: RequestCategory) = prefs.setFilter(requestCategory.title)

    fun finishOrder(id: String) = api.service.finishOrder(authRepository.getToken().blockingGet()!!, id)

    fun acceptRequest(id: String) = api.service.acceptOrder(authRepository.getToken().blockingGet()!!, id)

    fun declineRequest(id: String) = api.service.declineOrder(authRepository.getToken().blockingGet()!!, id)

    fun cancelOrder(id: String, desc: String) = api.service.cancelOrder(authRepository.getToken().blockingGet()!!, id, desc)
}