package com.example.asbolsyn.order.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asbolsyn.R
import com.example.asbolsyn.databinding.FragmentOrderDetailsBinding
import com.example.asbolsyn.order.data.model.OrderItemResponse
import com.example.asbolsyn.order.presentation.adapter.OrderListAdapter
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

//    private val compositeAdapter by lazy {
//        CompositeAdapter.Builder().apply {
//
//        }.build()
//    }

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

            }
        }
    }

    private fun setupViews() {
        with(binding) {
//            orderDetailsRecyclerView.adapter = compositeAdapter
        }
    }

    private fun submitList(items: List<DelegateAdapterItem>) {

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

    }
}