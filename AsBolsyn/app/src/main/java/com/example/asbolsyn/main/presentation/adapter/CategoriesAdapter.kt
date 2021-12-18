package com.example.asbolsyn.main.presentation.adapter

import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.asbolsyn.R
import com.example.asbolsyn.databinding.ItemMainCategoryBinding
import com.example.asbolsyn.extensions.dp
import com.example.asbolsyn.main.data.model.CategoriesResponse

class CategoriesAdapter(private var categories: List<CategoriesResponse.Item> = listOf()) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    fun updateItems(categories: List<CategoriesResponse.Item>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemMainCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size

    inner class ViewHolder(
        private val binding: ItemMainCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: CategoriesResponse.Item) {
            with(binding) {
                categoryTitle.text = category.categoryName
                Glide.with(root.context)
                    .load(category.img)
                    .circleCrop()
                    .transform(RoundedCorners(30.dp))
                    .placeholder(R.drawable.ic_category_placeholder)
                    .error(R.drawable.ic_restaurant_placeholder)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .fitCenter()
                    .into(categoryImage)
            }
        }
    }
}