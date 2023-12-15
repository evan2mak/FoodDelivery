package evtomak.iu.edu.fooddelivery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adapter for displaying recent restaurant items in a RecyclerView.
class RecentRestaurantsAdapter(
    private val recentRestaurantList: List<String>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecentRestaurantsAdapter.ViewHolder>() {

    // Interface for defining a click listener
    interface OnItemClickListener {
        fun onItemClick(restaurantName: String)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Bind data to the view holder
        fun bind(restaurantName: String) {
            val restaurantNameTextView = itemView.findViewById<TextView>(R.id.restaurantNameTextView)
            restaurantNameTextView.text = restaurantName

            // Set click listener to trigger onItemClick when a restaurant item is clicked
            itemView.setOnClickListener { itemClickListener.onItemClick(restaurantName) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view holder when needed
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recent_restaurant, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind data to the view holder for a specific position
        holder.bind(recentRestaurantList[position])
    }

    override fun getItemCount(): Int {
        // Return the total number of items in the list
        return recentRestaurantList.size
    }
}
