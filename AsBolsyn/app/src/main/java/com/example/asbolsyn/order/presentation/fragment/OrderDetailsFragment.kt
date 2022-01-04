package com.example.asbolsyn.order.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asbolsyn.R
import com.example.asbolsyn.databinding.FragmentOrderDetailsBinding
import com.example.asbolsyn.order.data.model.OrderItemResponse
import com.example.asbolsyn.order.presentation.adapter.*
import com.example.asbolsyn.order.presentation.viewmodel.*
import com.example.asbolsyn.utils.AlertManager
import com.example.asbolsyn.utils.delegateadapter.CompositeAdapter
import com.example.asbolsyn.utils.delegateadapter.DelegateAdapterItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderDetailsFragment : Fragment() {

    private var _binding: FragmentOrderDetailsBinding? = null
    private val binding: FragmentOrderDetailsBinding
        get() = _binding!!

    private val viewModel by viewModel<OrderDetailsViewModel>()

    private val orderDetailsHeaderDelegateAdapter by lazy {
        OrderDetailsHeaderDelegateAdapter(
            onDetailsClicked = {

            }
        )
    }

    private val orderDetailsGuestsDelegateAdapter by lazy {
        OrderDetailsGuestsDelegateAdapter()
    }

    private val orderDetailsMenuDelegateAdapter by lazy {
        OrderDetailsMenuDelegateAdapter()
    }

    private val orderDetailsPricingDelegateAdapter by lazy {
        OrderDetailsPricingDelegateAdapter()
    }

    private val compositeAdapter by lazy {
        CompositeAdapter.Builder().apply {
            add(orderDetailsHeaderDelegateAdapter)
            add(orderDetailsGuestsDelegateAdapter)
            add(orderDetailsMenuDelegateAdapter)
            add(orderDetailsPricingDelegateAdapter)
        }.build()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        configureObservers()

        viewModel.dispatch(OrderDetailsAction.LoadOrderDetails)
    }

    private fun configureObservers() {
        viewModel.orderDetailsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is OrderDetailsState.LoadingState -> configureLoadingState(state.isLoading)
                is OrderDetailsState.Error -> showError(state.message) { findNavController().navigateUp() }
                is OrderDetailsState.SubmitList -> submitList(state.items)
            }
        }
    }

    private fun setupViews() {
        with(binding) {
            orderDetailsRecyclerView.adapter = compositeAdapter

            backImage.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun submitList(items: List<DelegateAdapterItem>) {
        compositeAdapter.submitList(items)
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