package com.example.asbolsyn.main.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.asbolsyn.databinding.ItemMainCategoryBinding
import com.example.asbolsyn.main.presentation.model.MainCategory

class CategoriesAdapter(private val categories: List<MainCategory>) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemMainCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size

    inner class ViewHolder(
        private val binding: ItemMainCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: MainCategory) {
            with(binding) {
                categoryTitle.text = category.title
                categoryImage.setImageResource(category.icon)
            }
        }
    }

}