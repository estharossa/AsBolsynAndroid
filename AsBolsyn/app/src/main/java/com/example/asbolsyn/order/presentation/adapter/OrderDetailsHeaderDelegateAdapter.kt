package com.example.asbolsyn.order.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.asbolsyn.R
import com.example.asbolsyn.databinding.ItemOrderDetailsHeaderBinding
import com.example.asbolsyn.order.presentation.adaptermodel.OrderDetailsHeaderAdapterModel
import com.example.asbolsyn.utils.DateConstants
import com.example.asbolsyn.utils.delegateadapter.DelegateAdapter
import com.example.asbolsyn.utils.delegateadapter.DelegateAdapterItem
import com.example.asbolsyn.utils.extensions.getCompatColor
import com.example.asbolsyn.utils.extensions.toDate
import com.example.asbolsyn.utils.extensions.toString

class OrderDetailsHeaderDelegateAdapter(
    private val onDetailsClicked: () -> Unit
) : DelegateAdapter<OrderDetailsHeaderAdapterModel, OrderDetailsHeaderDelegateAdapter.ViewHolder>(
    OrderDetailsHeaderAdapterModel::class.java
) {

    override fun createViewHolder(parent: ViewGroup) =
        ViewHolder(
            ItemOrderDetailsHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun bindViewHolder(
        model: OrderDetailsHeaderAdapterModel,
        viewHolder: ViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>
    ) {
        viewHolder.bind(model)
    }

    inner class ViewHolder(
        private val binding: ItemOrderDetailsHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context

        fun bind(model: OrderDetailsHeaderAdapterModel) {
            with(binding) {
                val order = model.orderDetails

                orderNumber.text = context.getString(R.string.order_number_fmt, order.orderNumber)
                guestsNumber.text = context.getString(R.string.guests_number_fmt, order.guestsNumber)
                restaurantName.text = context.getString(R.string.order_restaurant_fmt, order.restaurantName)
                timeInfo.text = context.getString(
                    R.string.order_time_fmt,
                    order.time,
                    order.date.toDate(DateConstants.DATE).toString(DateConstants.D_MMMM),
                    order.date.toDate(DateConstants.DATE).toString(DateConstants.EE).capitalize()
                )

                if (order.isActive) {
                    orderStatus.text = context.getString(R.string.order_status_active)
                    orderStatus.setTextColor(context.getCompatColor(R.color.color_green))
                } else {
                    orderStatus.text = context.getString(R.string.order_status_inactive)
                    orderStatus.setTextColor(context.getCompatColor(R.color.color_gray_light))
                }

                detailsButton.setOnClickListener {
                    onDetailsClicked()
                }
            }
        }
    }
}