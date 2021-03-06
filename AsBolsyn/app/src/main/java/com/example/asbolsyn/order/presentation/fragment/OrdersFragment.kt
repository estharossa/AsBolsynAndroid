package com.example.asbolsyn.order.presentation.fragment

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
import com.example.asbolsyn.databinding.FragmentConnectBinding
import com.example.asbolsyn.databinding.FragmentFriendsInvitationBinding
import com.example.asbolsyn.databinding.FragmentOrderBinding
import com.example.asbolsyn.databinding.LayoutToolbarBinding
import com.example.asbolsyn.order.data.model.OrderItemResponse
import com.example.asbolsyn.order.presentation.adapter.OrderListAdapter
import com.example.asbolsyn.order.presentation.viewmodel.CabinetOrdersViewModel
import com.example.asbolsyn.order.presentation.viewmodel.OrderListAction
import com.example.asbolsyn.order.presentation.viewmodel.OrderListState
import com.example.asbolsyn.utils.AlertManager
import com.example.asbolsyn.utils.DateConstants
import com.example.asbolsyn.utils.extensions.toDate
import com.example.asbolsyn.utils.extensions.toString
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrdersFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null
    private val binding: FragmentOrderBinding
        get() = _binding!!

    private val viewModel by viewModel<CabinetOrdersViewModel>()
    private var ordersAdapter: OrderListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        configureObservers()

        viewModel.dispatch(OrderListAction.LoadOrders)
    }

    private fun configureObservers() {
        viewModel.orderListState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is OrderListState.Error -> showError(state.message) {}
                is OrderListState.LoadingState -> configureLoadingState(state.isLoading)
                is OrderListState.SubmitList -> submitList(state.orders)
            }
        }
    }

    private fun setupViews() {
        with(binding) {
            ordersAdapter = OrderListAdapter(
                onOrderClick = {
                    val direction = OrdersFragmentDirections.actionOrdersFragmentToOrderDetailsFragment()
                    findNavController().navigate(direction)
                }
            )

            with(ordersRecyclerView) {
                adapter = ordersAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }

            connect.setOnClickListener {
                showConnectDialog()
            }
        }
    }

    private fun showConnectDialog() {
        val binding = FragmentConnectBinding.inflate(LayoutInflater.from(requireContext()))
        val orderDialog = AlertManager.getCustomAlert(requireContext(), binding.root)

        with(binding) {
            connect.setOnClickListener {
                orderDialog.dismiss()

                val direction = OrdersFragmentDirections.actionOrdersFragmentToOrderDetailsFragment()
                findNavController().navigate(direction)
            }
        }

        with(orderDialog) {
            window?.setBackgroundDrawable(InsetDrawable(ColorDrawable(Color.TRANSPARENT), 20))
            show()
        }
    }

    private fun submitList(orders: List<OrderItemResponse>) {
        ordersAdapter?.updateItems(orders)
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