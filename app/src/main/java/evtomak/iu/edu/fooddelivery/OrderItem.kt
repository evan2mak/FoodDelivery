package evtomak.iu.edu.fooddelivery

// Order item model
data class OrderItem(
    val foodName: String = "",
    val price: Double = 0.0,
    var quantity: Int = 0,
    var restaurantName: String = ""
)

