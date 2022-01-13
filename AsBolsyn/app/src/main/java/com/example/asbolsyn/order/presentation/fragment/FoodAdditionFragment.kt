package com.example.asbolsyn.order.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.asbolsyn.R
import com.example.asbolsyn.databinding.FragmentFoodAdditionBinding
import com.example.asbolsyn.order.data.model.MenuResponse
import com.example.asbolsyn.order.presentation.adapter.MenuAdapter
import com.example.asbolsyn.order.presentation.viewmodel.MenuAction
import com.example.asbolsyn.order.presentation.viewmodel.MenuState
import com.example.asbolsyn.order.presentation.viewmodel.RestaurantMenuViewModel
import com.example.asbolsyn.utils.AlertManager
import org.koin.androidx.viewmodel.ext.android.viewModel

class FoodAdditionFragment : Fragment() {

    private var _binding: FragmentFoodAdditionBinding? = null
    private val binding: FragmentFoodAdditionBinding
        get() = _binding!!

    private val viewModel by viewModel<RestaurantMenuViewModel>()

    private var menuAdapter: MenuAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodAdditionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        configureObservers()

        viewModel.dispatch(MenuAction.LoadMenu)
    }

    private fun configureObservers() {
        viewModel.menuState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MenuState.LoadingState -> configureLoadingState(state.isLoading)
                is MenuState.Error -> showError(state.message) { findNavController().navigateUp() }
                is MenuState.SubmitList -> showUI(state.menu)
            }
        }
    }

    private fun setupViews() {
        with(binding) {
            backImage.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun showUI(menu: MenuResponse) {
        with(binding) {
            restaurantName.text = menu.restaurantName
            restaurantInfo.text = menu.restaurantInfo.joinToString(" • ")
            rating.text = menu.rating

            menuAdapter = MenuAdapter(menuItems = menu.items)

            menuRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            menuRecyclerView.adapter = menuAdapter
        }
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

    private fun configureLoadingState(isLoading: Boolean) {
        binding.container.isGone = isLoading
        binding.progressBar.isGone = !isLoading
    }
}