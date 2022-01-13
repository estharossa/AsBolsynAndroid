package com.example.asbolsyn.order.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.asbolsyn.databinding.ItemMenuElementBinding
import com.example.asbolsyn.order.data.model.MenuResponse
import com.example.asbolsyn.utils.extensions.priceString

class MenuAdapter(
    private var menuItems: List<MenuResponse.Item> = listOf()
) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    fun updateItems(menuItemList: List<MenuResponse.Item>) {
        this.menuItems = menuItemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemMenuElementBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(menuItems[position])
    }

    override fun getItemCount(): Int = menuItems.size

    inner class ViewHolder(
        private val binding: ItemMenuElementBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(menuItem: MenuResponse.Item) {
            with(binding) {
                name.text = menuItem.name
                description.text = menuItem.description
                duration.text = menuItem.duration.toString()
                rating.text = menuItem.rating
                price.text = menuItem.price.priceString
            }
        }
    }
}