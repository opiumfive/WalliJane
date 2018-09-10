package com.iterika.walli.domain

import com.iterika.walli.model.RequestCategory
import com.iterika.walli.repos.RequestRepository
import javax.inject.Inject

class RequestInteractor @Inject constructor(private val repository: RequestRepository) {

    fun applyFilter(requestCategory: RequestCategory)= repository.setFilter(requestCategory)

    fun getFilter() = repository.getFilter()

    fun getRequests() = repository.getRequests()

    fun getFilterCats() = repository.getFilterCats()

    fun acceptRequest(id: String) = repository.acceptRequest(id)

    fun declineRequest(id: String) = repository.declineRequest(id)

    fun finishOrder(id: String) = repository.finishOrder(id)

    fun cancelOrder(id: String, desc: String) = repository.cancelOrder(id, desc)
}