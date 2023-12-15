package evtomak.iu.edu.fooddelivery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// OrderViewModel: Manages order-related data for the application.
class OrderViewModel : ViewModel() {
    private val _selectedFoodItems = MutableLiveData<List<OrderItem>>()

    private val _restaurantName = MutableLiveData<String>()

    // LiveData for restaurant name
    val restaurantName: LiveData<String>
        get() = _restaurantName

    // Function to set the restaurant name
    fun setRestaurantName(name: String) {
        _restaurantName.value = name
    }

    // LiveData for selected food items
    val selectedFoodItems: LiveData<List<OrderItem>>
        get() = _selectedFoodItems

    // Function to set the selected food items
    fun setSelectedFoodItems(items: List<OrderItem>) {
        _selectedFoodItems.value = items.filter { it.quantity > 0 }
    }

    private val _recentOrder = MutableLiveData<Order?>()

    // LiveData for the most recent order
    val recentOrder: LiveData<Order?>
        get() = _recentOrder

    // Function to set the most recent order
    fun setRecentOrder(order: Order?) {
        _recentOrder.value = order
    }
}
