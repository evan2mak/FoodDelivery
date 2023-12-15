package evtomak.iu.edu.fooddelivery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderViewModel : ViewModel() {
    private val _selectedFoodItems = MutableLiveData<List<OrderItem>>()

    private val _restaurantName = MutableLiveData<String>()

    val restaurantName: LiveData<String>
        get() = _restaurantName

    fun setRestaurantName(name: String) {
        _restaurantName.value = name
    }

    val selectedFoodItems: LiveData<List<OrderItem>>
        get() = _selectedFoodItems

    fun setSelectedFoodItems(items: List<OrderItem>) {
        _selectedFoodItems.value = items.filter { it.quantity > 0 }
    }

    private val _recentOrder = MutableLiveData<Order?>()

    val recentOrder: LiveData<Order?>
        get() = _recentOrder

    fun setRecentOrder(order: Order?) {
        _recentOrder.value = order
    }
}
