package com.example.asbolsyn.main.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.asbolsyn.R
import com.example.asbolsyn.databinding.ItemMainRestaurantBinding
import com.example.asbolsyn.main.data.model.RestaurantsResponse

class RestaurantsAdapter(
    private var restaurants: List<RestaurantsResponse.Item> = listOf(),
    private val onOrderClick: () -> Unit
) : RecyclerView.Adapter<RestaurantsAdapter.ViewHolder>() {

    fun updateItems(restaurants: List<RestaurantsResponse.Item>) {
        this.restaurants = restaurants
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemMainRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(restaurants[position])
    }

    override fun getItemCount(): Int = restaurants.size

    inner class ViewHolder(
        private val binding: ItemMainRestaurantBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(restaurant: RestaurantsResponse.Item) {
            with(binding) {
                backgroundImage.setImageResource(R.drawable.ic_restaurant_placeholder)
                title.text = restaurant.restaurantName
                category.text = restaurant.category

                Glide.with(root.context)
                    .load(restaurant.img)
                    .placeholder(R.drawable.ic_restaurant_placeholder)
                    .error(R.drawable.ic_restaurant_placeholder)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .fitCenter()
                    .into(backgroundImage)

                orderButton.setOnClickListener {
                    onOrderClick()
                }
            }
        }
    }
}