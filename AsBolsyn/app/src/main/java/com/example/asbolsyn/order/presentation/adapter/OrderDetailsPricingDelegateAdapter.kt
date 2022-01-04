package com.example.asbolsyn.order.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.example.asbolsyn.databinding.ItemOrderDetailsPricingBinding
import com.example.asbolsyn.databinding.LayoutPriceBinding
import com.example.asbolsyn.order.data.model.OrderDetailsResponse
import com.example.asbolsyn.order.presentation.adaptermodel.OrderDetailsPricingAdapterModel
import com.example.asbolsyn.utils.delegateadapter.DelegateAdapter
import com.example.asbolsyn.utils.delegateadapter.DelegateAdapterItem
import com.example.asbolsyn.utils.extensions.priceString

class OrderDetailsPricingDelegateAdapter :
    DelegateAdapter<OrderDetailsPricingAdapterModel, OrderDetailsPricingDelegateAdapter.ViewHolder>(
        OrderDetailsPricingAdapterModel::class.java
    ) {

    override fun createViewHolder(parent: ViewGroup) =
        ViewHolder(
            ItemOrderDetailsPricingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun bindViewHolder(
        model: OrderDetailsPricingAdapterModel,
        viewHolder: ViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>
    ) {
        viewHolder.bind(model)
    }

    inner class ViewHolder(
        private val binding: ItemOrderDetailsPricingBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context

        fun bind(model: OrderDetailsPricingAdapterModel) {
            with(binding) {

                if (pricingLayout.childCount != 0) {
                    pricingLayout.removeAllViews()
                }

                createPricingLayout(model.orderDetails.guests, root).forEach {
                    pricingLayout.addView(it)
                }

                totalPrice.text = model.orderDetails.totalPrice.priceString
                yourPrice.text = model.orderDetails.yourPrice.priceString

                if (model.orderDetails.guestsNumber == 0) {
                    listOf(pricingLayout, separatorView, totalPriceLayout, friendsCheckbox).forEach {
                        it.isGone = true
                    }
                }
            }
        }

        private fun createPricingLayout(
            guests: List<OrderDetailsResponse.Guest>,
            parent: ViewGroup
        ): List<View> =
            guests.map {
                LayoutPriceBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
                    dishName.text = it.name
                    dishPrice.text = it.totalPrice.priceString
                }.root
            }
    }
}