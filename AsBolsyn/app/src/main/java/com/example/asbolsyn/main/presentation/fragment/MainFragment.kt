package com.example.asbolsyn.main.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asbolsyn.R
import com.example.asbolsyn.databinding.FragmentMainBinding
import com.example.asbolsyn.main.presentation.adapter.CategoriesAdapter
import com.example.asbolsyn.main.presentation.adapter.RestaurantsAdapter
import com.example.asbolsyn.main.presentation.model.MainCategory
import com.example.asbolsyn.main.presentation.model.MainRestaurant

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!

    private var categoriesAdapter: CategoriesAdapter? = null
    private var restaurantsAdapter: RestaurantsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        with(binding) {
            categoriesAdapter = CategoriesAdapter(getCategories())

            with(categoriesRecyclerView) {
                adapter = categoriesAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }

            restaurantsAdapter = RestaurantsAdapter(getRestaurants())
            with(restaurantsRecyclerView) {
                adapter = restaurantsAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    private fun getCategories() = listOf<MainCategory>(
        MainCategory(
            title = "FastFood",
            icon = R.drawable.ic_category_fastfood
        ),
        MainCategory(
            title = "Завтрак",
            icon = R.drawable.ic_category_breakfast
        ),
        MainCategory(
            title = "Ужин",
            icon = R.drawable.ic_category_dinner
        ),
        MainCategory(
            title = "Обед",
            icon = R.drawable.ic_category_lunch
        ),
        MainCategory(
            title = "Русская кухня",
            icon = R.drawable.ic_category_placeholder
        ),
        MainCategory(
            title = "Восточная Кухня",
            icon = R.drawable.ic_category_placeholder
        ),
        MainCategory(
            title = "Корейская кухня",
            icon = R.drawable.ic_category_placeholder
        ),
        MainCategory(
            title = "Индийская кухня",
            icon = R.drawable.ic_category_placeholder
        )
    )

    private fun getRestaurants() = listOf<MainRestaurant>(
        MainRestaurant(
            title = "Del Papa",
            category = "Итальянская",
            freeSeatsNumber = 4,
            rate = 5.0,
            backgroundImage = R.drawable.ic_restaurant_placeholder
        ),
        MainRestaurant(
            title = "Del Papa",
            category = "Итальянская",
            freeSeatsNumber = 4,
            rate = 5.0,
            backgroundImage = R.drawable.ic_restaurant_placeholder
        ),
        MainRestaurant(
            title = "Del Papa",
            category = "Итальянская",
            freeSeatsNumber = 4,
            rate = 5.0,
            backgroundImage = R.drawable.ic_restaurant_placeholder
        )
    )

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}