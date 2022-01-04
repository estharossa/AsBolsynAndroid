package com.example.asbolsyn.order.presentation.adaptermodel

import com.example.asbolsyn.order.data.model.OrderDetailsResponse
import com.example.asbolsyn.utils.delegateadapter.DelegateAdapterItem


data class OrderDetailsGuestsAdapterModel(
    val orderDetails: OrderDetailsResponse
) : DelegateAdapterItem {

    override fun id() = orderDetails

    override fun content() = orderDetails

    override fun payload(other: Any): DelegateAdapterItem.Payloadable = DelegateAdapterItem.ChangePayload.None
}