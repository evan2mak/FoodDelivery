package evtomak.iu.edu.fooddelivery

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// OrderScreenFragment: Displays order details and allows tracking of orders.
class OrderScreenFragment : Fragment() {
    private val viewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_screen, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val restaurantNameTextView = view.findViewById<TextView>(R.id.tvRestaurantName)
        val orderDetailsTextView = view.findViewById<TextView>(R.id.tvOrderDetails)
        val totalPriceTextView = view.findViewById<TextView>(R.id.tvTotalPrice)
        val deliveryAddressTextView = view.findViewById<TextView>(R.id.tvDeliveryAddress)
        val specialInstructionsTextView = view.findViewById<TextView>(R.id.tvSpecialInstructions)
        val orderTimeTextView = view.findViewById<TextView>(R.id.tvOrderTime)

        viewModel.restaurantName.observe(viewLifecycleOwner) { restaurantName ->
            restaurantName?.let {
                restaurantNameTextView.text = "Restaurant Name: $restaurantName"
            }
        }

        viewModel.recentOrder.observe(viewLifecycleOwner) { recentOrder ->
            recentOrder?.let {
                orderDetailsTextView.text = "Order Details: ${getOrderDetails(recentOrder)}"
                totalPriceTextView.text = "Total Price: $${recentOrder.totalAmount}"
                deliveryAddressTextView.text = "Delivery Address: ${recentOrder.deliveryAddress}"
                specialInstructionsTextView.text =
                    "Special Instructions: ${recentOrder.specialInstructions}"
                orderTimeTextView.text = "Order Time: ${formatOrderTime(recentOrder.orderTime)}"
            }
        }

        val trackOrderButton = view.findViewById<Button>(R.id.trackOrder)
        trackOrderButton.setOnClickListener {
            showTrackOrderDialog()
        }

        val homeButton = view.findViewById<Button>(R.id.btnHome)
        homeButton.setOnClickListener {
            findNavController().navigate(R.id.action_order_screen_Fragment_to_homeFragment)
        }
    }

    // getOrderDetails: Get a formatted string representing order details.
    private fun getOrderDetails(order: Order): String {
        val orderItems = order.orderItems
        val orderDetails = StringBuilder()

        for (item in orderItems) {
            orderDetails.append("${item.foodName} (Quantity: ${item.quantity}), ")
        }

        if (orderDetails.isNotEmpty()) {
            orderDetails.delete(orderDetails.length - 2, orderDetails.length)
        }

        return orderDetails.toString()
    }

    // formatOrderTime: Format the order time as a string.
    private fun formatOrderTime(orderTime: Long): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val orderDate = Date(orderTime)
        return dateFormat.format(orderDate)
    }

    // showTrackOrderDialog: Display a dialog for tracking the order.
    private fun showTrackOrderDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_track_order)
        dialog.findViewById<ImageView>(R.id.imageViewTrackOrder).apply {
            setImageResource(R.drawable.google)
            setOnClickListener { dialog.dismiss() }
        }
        dialog.setCancelable(true)
        dialog.show()
    }
}
