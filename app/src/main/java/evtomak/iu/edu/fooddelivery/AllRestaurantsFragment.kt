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

class AllRestaurantsFragment : Fragment() {

    private val allRestaurantList = mutableListOf<String>()
    private val restaurantIdToNameMap = mutableMapOf<String, String>()
    private lateinit var database: FirebaseDatabase
    private lateinit var restaurantRef: DatabaseReference

    companion object {
        fun newInstance(): AllRestaurantsFragment {
            return AllRestaurantsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_restaurants, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewAllRestaurants)
        val adapter = AllRestaurantsAdapter(
            allRestaurantList,
            object : AllRestaurantsAdapter.OnItemClickListener {
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
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        database = FirebaseDatabase.getInstance()
        restaurantRef = database.getReference("restaurants")

        restaurantRef
            .orderByKey()
            .limitToFirst(8)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    allRestaurantList.clear()
                    restaurantIdToNameMap.clear() // Clear the map

                    for (snapshot in dataSnapshot.children) {
                        val restaurantId = snapshot.key
                        val restaurant = snapshot.getValue(Restaurant::class.java)

                        restaurantId?.let {
                            restaurant?.name?.let { name ->
                                allRestaurantList.add(name)
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
