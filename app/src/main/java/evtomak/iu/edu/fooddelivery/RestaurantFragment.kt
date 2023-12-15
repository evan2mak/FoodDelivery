package evtomak.iu.edu.fooddelivery

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.database.*

class RestaurantFragment : Fragment() {

    private lateinit var restaurantId: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var checkoutButton: Button
    private var foodItems: MutableList<OrderItem> = mutableListOf()
    private lateinit var database: FirebaseDatabase
    private lateinit var foodItemsRef: DatabaseReference
    private val viewModel: OrderViewModel by activityViewModels()
    private lateinit var viewPager: ViewPager2
    private lateinit var imagesAdapter: RestaurantImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restaurantId = arguments?.getString("restaurantId") ?: ""
        database = FirebaseDatabase.getInstance()
        foodItemsRef = database.getReference("restaurants/$restaurantId/orderItems")

        database.getReference("restaurants/$restaurantId/name").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val restaurantName = dataSnapshot.getValue(String::class.java) ?: "Default Restaurant"
                viewModel.setRestaurantName(restaurantName)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("RestaurantFragment", "Database error: $databaseError")
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_restaurant, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewFoodItems)
        checkoutButton = view.findViewById(R.id.btnCheckout)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = FoodItemAdapter(foodItems)
        recyclerView.adapter = adapter
        viewPager = view.findViewById(R.id.viewPagerRestaurantImages)
        imagesAdapter = RestaurantImagesAdapter(getRestaurantImages(restaurantId))
        viewPager.adapter = imagesAdapter

        checkoutButton.setOnClickListener {
            viewModel.setSelectedFoodItems(foodItems)
            val bundle = Bundle()
            bundle.putString("restaurantId", restaurantId)
            bundle.putString("restaurantName", viewModel.restaurantName.value ?: "")
            findNavController().navigate(R.id.action_restaurantFragment_to_checkoutFragment, bundle)
        }

        loadFoodItems(adapter)
        return view
    }

    private fun loadFoodItems(adapter: FoodItemAdapter) {
        foodItemsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("RestaurantFragment", "DataSnapshot: $dataSnapshot")
                foodItems.clear()
                dataSnapshot.children.forEach { snapshot ->
                    Log.d("RestaurantFragment", "Snapshot: $snapshot")
                    val foodItem = snapshot.getValue(OrderItem::class.java)
                    Log.d("RestaurantFragment", "Food Item: $foodItem")
                    foodItem?.let {
                        it.restaurantName = arguments?.getString("restaurantName") ?: ""
                        foodItems.add(it)
                    }
                }
                Log.d("RestaurantFragment", "Loaded items: ${foodItems.size}")
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }
    private fun getRestaurantImages(restaurantId: String): List<Int> {
        if (restaurantId == "restaurantId1") {
            return listOf(R.drawable.dominos1, R.drawable.dominos2, R.drawable.dominos3)
        }
        else if (restaurantId == "restaurantId2") {
            return listOf(R.drawable.ph1, R.drawable.ph2, R.drawable.ph3)
        }
        else if (restaurantId == "restaurantId3") {
            return listOf(R.drawable.mc1, R.drawable.mc2, R.drawable.mc3)
        }
        else if (restaurantId == "restaurantId4") {
            return listOf(R.drawable.sb1, R.drawable.sb2, R.drawable.sb3)
        }
        else if (restaurantId == "restaurantId5") {
            return listOf(R.drawable.bk1, R.drawable.bk2, R.drawable.bk3)
        }
        else if (restaurantId == "restaurantId6") {
            return listOf(R.drawable.kfc1, R.drawable.kfc2, R.drawable.kfc3)
        }
        else if (restaurantId == "restaurantId7") {
            return listOf(R.drawable.tb1, R.drawable.tb2, R.drawable.tb3)
        }
        else if (restaurantId == "restaurantId8") {
            return listOf(R.drawable.w1, R.drawable.w2, R.drawable.w3)
        }
        else{
            return listOf()
        }
    }
}