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

// CalendarViewFragment: Fragment responsible for displaying a calendar view and calculating total spent.
class CalendarViewFragment : Fragment() {
    private lateinit var calendarView: CalendarView
    private val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    private val databaseReference = FirebaseDatabase.getInstance().reference
    private var valueEventListener: ValueEventListener? = null

    // onCreateView: Inflates the fragment's layout and sets up the calendar view and button click listener.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar_view, container, false)
        calendarView = view.findViewById(R.id.calendarView)

        // Set up the date change listener for the calendar view.
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val selectedDate = calendar.time
            val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate)

            // Calculate and display the total spent for the selected date.
            calculateTotalSpent(formattedDate)
        }

        val homeButton = view.findViewById<Button>(R.id.buttonHome)
        homeButton.setOnClickListener {
            // Navigate back to the HomeFragment when the home button is clicked.
            findNavController().navigate(R.id.calendarViewFragment_to_Home)
        }

        return view
    }

    // calculateTotalSpent: Calculates the total amount spent by the user on a selected date range.
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

    // showTotalSpent: Displays the total amount spent as a toast message.
    private fun showTotalSpent(totalSpent: Double) {
        if (isAdded) {
            val formattedTotal = String.format("%.2f", totalSpent)
            Toast.makeText(requireContext(), "Total spent on selected date: $$formattedTotal", Toast.LENGTH_SHORT).show()
        }
    }

    // onDestroyView: Removes the ValueEventListener to prevent memory leaks when the fragment is destroyed.
    override fun onDestroyView() {
        super.onDestroyView()
        valueEventListener?.let { databaseReference.removeEventListener(it) }
    }
}
