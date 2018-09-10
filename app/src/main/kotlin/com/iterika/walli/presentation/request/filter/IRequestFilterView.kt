package com.iterika.walli.presentation.request.filter

import com.iterika.walli.model.RequestCategory
import com.iterika.walli.presentation.IView

interface IRequestFilterView : IView {
    fun showFilterList(list: List<RequestCategory>)
    fun updateChosenItem(item: RequestCategory)
}