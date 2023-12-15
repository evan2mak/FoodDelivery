package evtomak.iu.edu.fooddelivery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarViewFragment : Fragment() {
    private lateinit var calendarView: CalendarView
    private val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    private val databaseReference = FirebaseDatabase.getInstance().reference
    private var valueEventListener: ValueEventListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar_view, container, false)
        calendarView = view.findViewById(R.id.calendarView)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val selectedDate = calendar.time
            val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate)

            calculateTotalSpent(formattedDate)
        }

        val homeButton = view.findViewById<Button>(R.id.buttonHome)
        homeButton.setOnClickListener {
            findNavController().navigate(R.id.calendarViewFragment_to_Home)
        }

        return view
    }

    private fun calculateTotalSpent(selectedDate: String) {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = formatter.parse(selectedDate)

        val calendar = Calendar.getInstance()
        calendar.time = date
        val startOfDay = calendar.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        val endOfDay = calendar.apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.timeInMillis

        valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var totalSpent = 0.0
                dataSnapshot.children.forEach { snapshot ->
                    val order = snapshot.getValue(Order::class.java)
                    order?.let {
                        if (it.orderTime in startOfDay..endOfDay) {
                            totalSpent += it.totalAmount
                        }
                    }
                }
                showTotalSpent(totalSpent)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                if (isAdded) {
                    Toast.makeText(requireContext(), "Error fetching data", Toast.LENGTH_SHORT).show()
                }
            }
        }
        databaseReference.child("orders")
            .orderByChild("userId")
            .equalTo(userId)
            .addValueEventListener(valueEventListener!!)
    }

    private fun showTotalSpent(totalSpent: Double) {
        if (isAdded) {
            val formattedTotal = String.format("%.2f", totalSpent)
            Toast.makeText(requireContext(), "Total spent on selected date: $$formattedTotal", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        valueEventListener?.let { databaseReference.removeEventListener(it) }
    }
}
