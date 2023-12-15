package evtomak.iu.edu.fooddelivery

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

// CheckoutFragment: Fragment responsible for managing the checkout process.
class CheckoutFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var deliveryAddressEditText: EditText
    private lateinit var specialInstructionsEditText: EditText
    private lateinit var modifyOrderButton: Button
    private lateinit var placeOrderButton: Button
    private lateinit var foodItems: MutableList<OrderItem>
    private lateinit var foodItemsAdapter: CheckoutFoodItemAdapter
    private val viewModel: OrderViewModel by activityViewModels()
    private val databaseReference = FirebaseDatabase.getInstance().reference

    private lateinit var restaurantId: String
    private lateinit var restaurantName: String

    private lateinit var deliveryService: Intent
    private lateinit var deliveryHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restaurantId = arguments?.getString("restaurantId") ?: ""
        restaurantName = arguments?.getString("restaurantName") ?: ""
        deliveryService = Intent(requireContext(), DeliveryService::class.java)
        deliveryHandler = Handler(Looper.getMainLooper())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_checkout, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewFoodItems)
        deliveryAddressEditText = view.findViewById(R.id.etDeliveryAddress)
        specialInstructionsEditText = view.findViewById(R.id.etSpecialInstructions)
        modifyOrderButton = view.findViewById(R.id.btnModifyOrder)
        placeOrderButton = view.findViewById(R.id.btnPlaceOrder)

        foodItems = mutableListOf()
        foodItemsAdapter = CheckoutFoodItemAdapter(foodItems)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = foodItemsAdapter

        viewModel.selectedFoodItems.observe(viewLifecycleOwner) { items ->
            foodItems.clear()
            foodItems.addAll(items)
            foodItemsAdapter.notifyDataSetChanged()
        }

        modifyOrderButton.setOnClickListener {
            viewModel.setRestaurantName(restaurantName)
            val bundle = Bundle()
            bundle.putString("restaurantId", restaurantId)
            bundle.putString("restaurantName", restaurantName)
            findNavController().navigate(R.id.action_checkoutFragment_to_restaurantFragment, bundle)
        }

        placeOrderButton.setOnClickListener {
            // Retrieve delivery address and special instructions
            val deliveryAddress = deliveryAddressEditText.text.toString()
            val specialInstructions = specialInstructionsEditText.text.toString()
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "Unknown User"

            val order = Order(
                restaurantName = restaurantName,
                userId = userId,
                orderItems = foodItems,
                totalAmount = calculateTotalAmount(foodItems),
                orderTime = System.currentTimeMillis(),
                deliveryAddress = deliveryAddress,
                specialInstructions = specialInstructions
            )

            val orderId = databaseReference.child("orders").push().key
            orderId?.let {
                databaseReference.child("orders").child(it).setValue(order)
                    .addOnSuccessListener {
                        foodItems.clear()
                        foodItemsAdapter.notifyDataSetChanged()
                    }
                    .addOnFailureListener {
                    }
            }

            viewModel.setRecentOrder(order)
            viewModel.setRestaurantName(restaurantName)

            startDeliveryTimer()

            findNavController().navigate(R.id.action_checkoutFragment_to_order_screen_Fragment)
        }

        return view
    }

    // calculateTotalAmount: Calculates the total amount for the food items in the order.
    private fun calculateTotalAmount(foodItems: List<OrderItem>): Double {
        var totalAmount = 0.0
        for (foodItem in foodItems) {
            totalAmount += foodItem.price * foodItem.quantity
        }
        return totalAmount
    }

    // startDeliveryTimer: Initiates the delivery timer and service.
    private fun startDeliveryTimer() {
        val random = Random()
        val deliveryTimeSeconds = random.nextInt(16) + 15 // 15 to 30 seconds
        val deliveryTimeMillis = deliveryTimeSeconds * 1000L

        deliveryService.putExtra(DeliveryService.EXTRA_DELIVERY_TIME, deliveryTimeMillis)
        requireContext().startService(deliveryService)

        deliveryHandler.postDelayed({
            createDeliveryNotification()
        }, deliveryTimeMillis)
    }

    // createDeliveryNotification: Creates a delivery notification.
    @SuppressLint("MissingPermission")
    private fun createDeliveryNotification() {
        val notificationId = 1
        val channelId = "DeliveryChannel"

        if (NotificationManagerCompat.from(requireContext()).areNotificationsEnabled()) {
            val notificationManager = NotificationManagerCompat.from(requireContext())

            val notificationBuilder = NotificationCompat.Builder(requireContext(), channelId)
                .setSmallIcon(com.google.android.material.R.drawable.ic_clock_black_24dp)
                .setContentTitle("Your food has arrived!")
                .setContentText("Enjoy your meal from $restaurantName.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelName = "Delivery Notifications"
                val channelDescription = "Notifications for food delivery"
                val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
                notificationChannel.description = channelDescription

                val notificationManager = requireContext().getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(notificationChannel)
            }

            notificationManager.notify(notificationId, notificationBuilder.build())
        } else {
        }
    }
}
