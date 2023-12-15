package evtomak.iu.edu.fooddelivery

// Restaurant data model
data class Restaurant(
    val name: String = "",
    val orderItems: Map<String, OrderItem>? = null
)

