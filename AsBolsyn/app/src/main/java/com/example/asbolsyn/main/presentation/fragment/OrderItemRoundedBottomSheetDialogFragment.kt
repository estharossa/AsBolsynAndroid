package com.example.asbolsyn.main.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.asbolsyn.R
import com.example.asbolsyn.databinding.FragmentOrderItemBottomSheetBinding
import com.example.asbolsyn.main.presentation.viewmodel.OrderAction
import com.example.asbolsyn.main.presentation.viewmodel.OrderState
import com.example.asbolsyn.main.presentation.viewmodel.OrderViewModel
import com.example.asbolsyn.utils.views.RoundedBottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderItemRoundedBottomSheetDialogFragment : RoundedBottomSheetDialogFragment() {

    companion object {

        fun newInstance() = OrderItemRoundedBottomSheetDialogFragment()
    }

    private var _binding: FragmentOrderItemBottomSheetBinding? = null
    private val binding: FragmentOrderItemBottomSheetBinding
        get() = _binding!!

    private val orderViewModel by viewModel<OrderViewModel>()

    var onOrderClicked: (() -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentOrderItemBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        configureObservers()

        orderViewModel.dispatch(OrderAction.InitOrder)
    }

    private fun configureObservers() {
        orderViewModel.orderState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is OrderState.SetupUI -> setupRestaurantInfo(state.freeTables, state.maxSeats)
                is OrderState.UpdateTime -> updateTime(state.time)
                is OrderState.UpdateSeats -> updateSeats(state.seats)
            }
        }
    }

    private fun updateSeats(seats: Int) {
        binding.seatCount.text = seats.toString()
    }

    private fun updateTime(time: String) {
        binding.orderTime.text = time
    }

    private fun setupRestaurantInfo(freeTables: Int, maximumSeats: Int) {
        with(binding) {
            availableSeats.text = getString(R.string.free_seats_fmt, freeTables)
            maxSeats.text = getString(R.string.maximum_amount_of_guests_fmt, maximumSeats)
        }
    }

    private fun setupViews() {
        with(binding) {
            timeIncrement.setOnClickListener {
                orderViewModel.dispatch(OrderAction.IncrementTime)
            }

            timeDecrement.setOnClickListener {
                orderViewModel.dispatch(OrderAction.DecrementTime)
            }

            seatIncrement.setOnClickListener {
                orderViewModel.dispatch(OrderAction.IncrementSeat)
            }

            seatDecrement.setOnClickListener {
                orderViewModel.dispatch(OrderAction.DecrementSeat)
            }

            orderButton.setOnClickListener {
                onOrderClicked?.invoke()
                dismiss()
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}