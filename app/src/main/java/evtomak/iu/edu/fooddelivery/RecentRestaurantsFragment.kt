package evtomak.iu.edu.fooddelivery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

// Fragment for displaying recent restaurant items and navigating to restaurant details.
class RecentRestaurantsFragment : Fragment() {

    private val recentRestaurantList = mutableListOf<String>()
    private val restaurantIdToNameMap = mutableMapOf<String, String>() // Mapping between IDs and names
    private lateinit var database: FirebaseDatabase
    private lateinit var restaurantRef: DatabaseReference

    companion object {
        fun newInstance(): RecentRestaurantsFragment {
            return RecentRestaurantsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recent_restaurants, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewRecentRestaurants)
        val adapter = RecentRestaurantsAdapter(recentRestaurantList, object : RecentRestaurantsAdapter.OnItemClickListener {
            override fun onItemClick(restaurantName: String) {
                val restaurantId = restaurantIdToNameMap.entries.firstOrNull { it.value == restaurantName }?.key
                restaurantId?.let {
                    val bundle = Bundle()
                    bundle.putString("restaurantId", it)

                    val navController = (parentFragment as? HomeFragment)?.findNavController()
                    navController?.navigate(R.id.action_homeFragment_to_restaurantFragment, bundle)
                }
            }
        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        database = FirebaseDatabase.getInstance()
        restaurantRef = database.getReference("restaurants")

        restaurantRef
            .orderByKey()
            .limitToFirst(5)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    recentRestaurantList.clear()
                    restaurantIdToNameMap.clear() // Clear the map

                    for (snapshot in dataSnapshot.children) {
                        val restaurantId = snapshot.key
                        val restaurant = snapshot.getValue(Restaurant::class.java)

                        restaurantId?.let {
                            restaurant?.name?.let { name ->
                                recentRestaurantList.add(name)
                                restaurantIdToNameMap[it] = name // Populate the map
                            }
                        }
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            })

        return view
    }
}
