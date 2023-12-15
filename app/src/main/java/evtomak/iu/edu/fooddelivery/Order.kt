package evtomak.iu.edu.fooddelivery

// Order model
data class Order(
    val id: String? = null,
    val restaurantName: String = "",
    val userId: String = "",
    val orderItems: List<OrderItem> = emptyList(),
    val totalAmount: Double = 0.0,
    val orderTime: Long = 0L,
    val deliveryAddress: String = "", // Add delivery address field
    val specialInstructions: String = "" // Add special instructions field
)
