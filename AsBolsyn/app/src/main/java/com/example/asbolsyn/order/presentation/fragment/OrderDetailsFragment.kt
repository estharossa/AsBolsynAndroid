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
import com.example.asbolsyn.databinding.FragmentFriendsInvitationBinding
import com.example.asbolsyn.databinding.FragmentOrderDetailsBinding
import com.example.asbolsyn.databinding.FragmentOrderSuccessInfoBinding
import com.example.asbolsyn.main.presentation.fragment.MainFragmentDirections
import com.example.asbolsyn.order.data.model.OrderDetailsResponse
import com.example.asbolsyn.order.data.model.OrderItemResponse
import com.example.asbolsyn.order.presentation.adapter.*
import com.example.asbolsyn.order.presentation.viewmodel.*
import com.example.asbolsyn.utils.AlertManager
import com.example.asbolsyn.utils.DateConstants
import com.example.asbolsyn.utils.delegateadapter.CompositeAdapter
import com.example.asbolsyn.utils.delegateadapter.DelegateAdapterItem
import com.example.asbolsyn.utils.extensions.toDate
import com.example.asbolsyn.utils.extensions.toString
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderDetailsFragment : Fragment() {

    private var _binding: FragmentOrderDetailsBinding? = null
    private val binding: FragmentOrderDetailsBinding
        get() = _binding!!

    private val viewModel by viewModel<OrderDetailsViewModel>()

    private val orderDetailsHeaderDelegateAdapter by lazy {
        OrderDetailsHeaderDelegateAdapter(
            onDetailsClicked = {
                viewModel.dispatch(OrderDetailsAction.InviteFriends)
            }
        )
    }

    private val orderDetailsGuestsDelegateAdapter by lazy {
        OrderDetailsGuestsDelegateAdapter()
    }

    private val orderDetailsMenuDelegateAdapter by lazy {
        OrderDetailsMenuDelegateAdapter(
            onAddFood = {
                val direction = OrderDetailsFragmentDirections.actionOrderDetailsFragmentToMenuRestaurantFragment()
                findNavController().navigate(direction)
            }
        )
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
                is OrderDetailsState.InviteFriends -> inviteFriends(state.orderDetails)
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

    private fun inviteFriends(orderDetails: OrderDetailsResponse) {
        val binding = FragmentFriendsInvitationBinding.inflate(LayoutInflater.from(requireContext()))
        val orderDialog = AlertManager.getCustomAlert(requireContext(), binding.root)

        with(binding) {
            restaurantInfo.text = getString(R.string.order_success_restaurant_fmt, orderDetails.restaurantName)
            dateInfo.text = getString(
                R.string.order_time_fmt,
                orderDetails.time,
                orderDetails.date.toDate(DateConstants.DATE).toString(DateConstants.D_MMMM),
                orderDetails.date.toDate(DateConstants.DATE).toString(DateConstants.EE).capitalize()
            )
            roomNumberInfo.text = getString(R.string.order_success_room_fmt, orderDetails.roomNumber)

            invite.setOnClickListener {
                orderDialog.dismiss()
            }
        }

        with(orderDialog) {
            window?.setBackgroundDrawable(InsetDrawable(ColorDrawable(Color.TRANSPARENT), 20))
            show()
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