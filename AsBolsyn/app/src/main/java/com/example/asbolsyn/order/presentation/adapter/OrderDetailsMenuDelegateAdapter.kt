package com.example.asbolsyn.order.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.asbolsyn.databinding.ItemOrderDetailsGuestsBinding
import com.example.asbolsyn.databinding.ItemOrderDetailsMenuBinding
import com.example.asbolsyn.databinding.LayoutGuestBinding
import com.example.asbolsyn.databinding.LayoutPriceBinding
import com.example.asbolsyn.order.data.model.OrderDetailsResponse
import com.example.asbolsyn.order.presentation.adaptermodel.OrderDetailsGuestsAdapterModel
import com.example.asbolsyn.order.presentation.adaptermodel.OrderDetailsMenuAdapterModel
import com.example.asbolsyn.utils.delegateadapter.DelegateAdapter
import com.example.asbolsyn.utils.delegateadapter.DelegateAdapterItem
import com.example.asbolsyn.utils.extensions.priceString

class OrderDetailsMenuDelegateAdapter(
    private val onAddFood: () -> Unit
) : DelegateAdapter<OrderDetailsMenuAdapterModel, OrderDetailsMenuDelegateAdapter.ViewHolder>(
    OrderDetailsMenuAdapterModel::class.java
) {

    override fun createViewHolder(parent: ViewGroup) =
        ViewHolder(
            ItemOrderDetailsMenuBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun bindViewHolder(
        model: OrderDetailsMenuAdapterModel,
        viewHolder: ViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>
    ) {
        viewHolder.bind(model)
    }

    inner class ViewHolder(
        private val binding: ItemOrderDetailsMenuBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context

        fun bind(model: OrderDetailsMenuAdapterModel) {
            with(binding) {

                if (pricingLayout.childCount != 0) {
                    pricingLayout.removeAllViews()
                }

                createPricingLayout(model.orderDetails.pricingList, root).forEach {
                    pricingLayout.addView(it)
                }

                addFood.setOnClickListener {
                    onAddFood()
                }
            }
        }

        private fun createPricingLayout(
            pricingList: List<OrderDetailsResponse.Pricing>,
            parent: ViewGroup
        ): List<View> =
            pricingList.map {
                LayoutPriceBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
                    dishName.text = it.item

                    if (it.count == 1) {
                        dishPrice.text = it.price.priceString
                    } else {
                        dishPrice.text = "${it.price.priceString} х ${it.count}"
                    }
                }.root
            }
    }
}