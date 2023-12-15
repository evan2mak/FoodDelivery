package evtomak.iu.edu.fooddelivery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// OrdersAdapter: Manages the RecyclerView adapter for displaying orders.
class OrdersAdapter : RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {
    private var orders = listOf<Order>()

    // setOrders: Update the list of orders and notify the adapter of data changes.
    fun setOrders(orders: List<Order>) {
        this.orders = orders
        notifyDataSetChanged()
    }

    // onCreateViewHolder: Create a new OrderViewHolder instance for each item in the RecyclerView.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    // onBindViewHolder: Bind data to the OrderViewHolder for a specific order in the RecyclerView.
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.restaurantNameTextView.text = "Ordered from: ${order.restaurantName}"
        holder.orderDetailsTextView.text = "Order Details: ${getOrderDetails(order)}"
        holder.totalPriceTextView.text = "Total Price: $${order.totalAmount}"
        holder.deliveryAddressTextView.text = "Delivery Address: ${order.deliveryAddress}"
        holder.specialInstructionsTextView.text = "Special Instructions: ${order.specialInstructions}"
        holder.orderTimeTextView.text = "Order Time: ${formatOrderTime(order.orderTime)}"
    }

    // getItemCount: Return the total number of orders in the RecyclerView.
    override fun getItemCount() = orders.size

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val restaurantNameTextView: TextView = itemView.findViewById(R.id.tvRestaurantName)
        val orderDetailsTextView: TextView = itemView.findViewById(R.id.tvOrderDetails)
        val totalPriceTextView: TextView = itemView.findViewById(R.id.tvTotalPrice)
        val deliveryAddressTextView: TextView = itemView.findViewById(R.id.tvDeliveryAddress)
        val specialInstructionsTextView: TextView = itemView.findViewById(R.id.tvSpecialInstructions)
        val orderTimeTextView: TextView = itemView.findViewById(R.id.tvOrderTime)
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
}
