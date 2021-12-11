package com.example.asbolsyn.main.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.asbolsyn.databinding.ItemMainRestaurantBinding
import com.example.asbolsyn.main.presentation.model.MainRestaurant

class RestaurantsAdapter(private val restaurants: List<MainRestaurant>) :
    RecyclerView.Adapter<RestaurantsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemMainRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(restaurants[position])
    }

    override fun getItemCount(): Int = restaurants.size

    inner class ViewHolder(
        private val binding: ItemMainRestaurantBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(restaurant: MainRestaurant) {
            with(binding) {
                backgroundImage.setImageResource(restaurant.backgroundImage)
                title.text = restaurant.title
                category.text = restaurant.category
            }
        }
    }
}