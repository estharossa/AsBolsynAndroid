package com.example.asbolsyn.main.presentation.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asbolsyn.R
import com.example.asbolsyn.databinding.FragmentMainBinding
import com.example.asbolsyn.databinding.FragmentOrderSuccessInfoBinding
import com.example.asbolsyn.main.data.model.CategoriesResponse
import com.example.asbolsyn.main.data.model.RestaurantsResponse
import com.example.asbolsyn.main.presentation.viewmodel.RestaurantsViewModel
import com.example.asbolsyn.main.presentation.adapter.CategoriesAdapter
import com.example.asbolsyn.main.presentation.adapter.RestaurantsAdapter
import com.example.asbolsyn.main.presentation.viewmodel.OrderAction
import com.example.asbolsyn.main.presentation.viewmodel.RestaurantsAction
import com.example.asbolsyn.main.presentation.viewmodel.RestaurantsState
import com.example.asbolsyn.utils.AlertManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!

    private var categoriesAdapter: CategoriesAdapter? = null
    private var restaurantsAdapter: RestaurantsAdapter? = null

    private val viewModel by viewModel<RestaurantsViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        configureObservers()

        viewModel.dispatch(RestaurantsAction.FetchRestaurants)
    }

    private fun configureObservers() {
        viewModel.restaurantsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is RestaurantsState.Error -> showError(state.message) { viewModel.dispatch(RestaurantsAction.FetchRestaurants) }
                is RestaurantsState.LoadingState -> configureLoadingState(state.isLoading)
                is RestaurantsState.RestaurantsLoaded -> submitList(state.restaurants, state.categories)
                is RestaurantsState.OrderSuccess -> handleSuccessOrder(state.roomNumber)
            }
        }
    }

    private fun setupViews() {
        with(binding) {
            categoriesAdapter = CategoriesAdapter()

            with(categoriesRecyclerView) {
                adapter = categoriesAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }

            restaurantsAdapter = RestaurantsAdapter(
                onOrderClick = {
                    openOrderBottomSheet()
                }
            )

            with(restaurantsRecyclerView) {
                adapter = restaurantsAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    private fun handleSuccessOrder(roomNumber: String) {
        val binding = FragmentOrderSuccessInfoBinding.inflate(LayoutInflater.from(requireContext()))
        val orderDialog = AlertManager.getCustomAlert(requireContext(), binding.root)

        with(binding) {
            restaurantInfo.text = getString(R.string.order_success_restaurant_fmt, "Del Papa")
            dateInfo.text = getString(R.string.order_success_date_fmt, "25.01.2020, 14-30")
            roomNumberInfo.text = getString(R.string.order_success_room_fmt, roomNumber)

            navigateButton.setOnClickListener {
                orderDialog.dismiss()

                val direction = MainFragmentDirections.actionMainFragmentToOrdersFragment()
                findNavController().navigate(direction)
            }
        }

        with(orderDialog) {
            window?.setBackgroundDrawable(InsetDrawable(ColorDrawable(Color.TRANSPARENT), 20))
            show()
        }
    }

    private fun openOrderBottomSheet() {
        OrderItemRoundedBottomSheetDialogFragment.newInstance().apply {
            onOrderClicked = {
                viewModel.dispatch(RestaurantsAction.OrderPlace)
            }
        }.also {
            it.show(childFragmentManager, it.tag)
        }
    }

    private fun submitList(restaurants: List<RestaurantsResponse.Item>, categories: List<CategoriesResponse.Item>) {
        restaurantsAdapter?.updateItems(restaurants)
        categoriesAdapter?.updateItems(categories)
    }

    private fun configureLoadingState(isLoading: Boolean) {
        binding.container.isGone = isLoading
        binding.progressBar.isGone = !isLoading
    }

    private fun showError(message: String? = null, action: (() -> Unit)? = null) {
        AlertManager.showOKAlert(
            context = requireContext(),
            message = message ?: getString(R.string.error_general),
            cancellable = false
        ) {
            action?.invoke()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}