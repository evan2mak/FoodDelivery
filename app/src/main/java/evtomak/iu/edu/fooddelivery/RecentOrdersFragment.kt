package evtomak.iu.edu.fooddelivery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController


class RecentOrdersFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val ordersAdapter = OrdersAdapter()
    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_orders, container, false)
        recyclerView = view.findViewById(R.id.ordersRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = ordersAdapter
        loadOrders()

        val homeButton = view.findViewById<Button>(R.id.buttonHome)
        homeButton.setOnClickListener {
            findNavController().navigate(R.id.action_orders_Fragment_to_homeFragment)
        }

        return view
    }

    private fun loadOrders() {
        databaseReference.child("orders").orderByChild("userId").equalTo(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val orders = mutableListOf<Order>()
                    dataSnapshot.children.forEach { snapshot ->
                        val order = snapshot.getValue(Order::class.java)
                        order?.let { orders.add(it) }
                    }
                    ordersAdapter.setOrders(orders)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
    }
}


