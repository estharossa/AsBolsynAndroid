package com.example.asbolsyn.order.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.asbolsyn.databinding.ItemOrderDetailsGuestsBinding
import com.example.asbolsyn.databinding.LayoutGuestBinding
import com.example.asbolsyn.order.data.model.OrderDetailsResponse
import com.example.asbolsyn.order.presentation.adaptermodel.OrderDetailsGuestsAdapterModel
import com.example.asbolsyn.utils.delegateadapter.DelegateAdapter
import com.example.asbolsyn.utils.delegateadapter.DelegateAdapterItem

class OrderDetailsGuestsDelegateAdapter :
    DelegateAdapter<OrderDetailsGuestsAdapterModel, OrderDetailsGuestsDelegateAdapter.ViewHolder>(
        OrderDetailsGuestsAdapterModel::class.java
    ) {

    override fun createViewHolder(parent: ViewGroup) =
        ViewHolder(
            ItemOrderDetailsGuestsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun bindViewHolder(
        model: OrderDetailsGuestsAdapterModel,
        viewHolder: ViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>
    ) {
        viewHolder.bind(model)
    }

    inner class ViewHolder(
        private val binding: ItemOrderDetailsGuestsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context

        fun bind(model: OrderDetailsGuestsAdapterModel) {
            with(binding) {

                if (guestsLayout.childCount != 0) {
                    guestsLayout.removeAllViews()
                }

                createGuestsLayout(model.orderDetails.guests, root).forEach {
                    guestsLayout.addView(it)
                }
            }
        }

        private fun createGuestsLayout(guests: List<OrderDetailsResponse.Guest>, parent: ViewGroup): List<View> =
            guests.map {
                LayoutGuestBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
                    guestName.text = it.name

                    checkoutMenu.setOnClickListener {

                    }
                }.root
            }
    }
}