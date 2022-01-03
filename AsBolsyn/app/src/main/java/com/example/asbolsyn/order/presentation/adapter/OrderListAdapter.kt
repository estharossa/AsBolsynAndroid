package com.example.asbolsyn.order.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.asbolsyn.R
import com.example.asbolsyn.databinding.ItemOrderBinding
import com.example.asbolsyn.order.data.model.OrderItemResponse
import com.example.asbolsyn.utils.extensions.getCompatColor

class OrderListAdapter(
    private var orders: List<OrderItemResponse> = listOf(),
    private val onOrderClick: () -> Unit
) : RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {

    fun updateItems(orders: List<OrderItemResponse>) {
        this.orders = orders
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount(): Int = orders.size

    inner class ViewHolder(
        private val binding: ItemOrderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context

        fun bind(order: OrderItemResponse) {
            with(binding) {
                orderNumber.text = context.getString(R.string.order_number_fmt, order.orderNumber)
                guestsNumber.text = context.getString(R.string.guests_number_fmt, order.guests)
                restaurantName.text = context.getString(R.string.order_restaurant_fmt, order.restaurantName)
                timeInfo.text = order.date + ", " + order.time

                if (order.isActive) {
                    orderStatus.text = context.getString(R.string.order_status_active)
                    orderStatus.setTextColor(context.getCompatColor(R.color.color_green))
                } else {
                    orderStatus.text = context.getString(R.string.order_status_inactive)
                    orderStatus.setTextColor(context.getCompatColor(R.color.color_gray_light))
                }

                root.setOnClickListener {
                    onOrderClick()
                }
            }
        }
    }
}