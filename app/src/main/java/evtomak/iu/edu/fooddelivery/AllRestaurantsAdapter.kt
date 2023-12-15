package evtomak.iu.edu.fooddelivery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// AllRestaurantsAdapter: Manages the RecyclerView adapter for displaying a list of restaurants.
class AllRestaurantsAdapter(
    private val allRestaurantList: List<String>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<AllRestaurantsAdapter.ViewHolder>() {

    // OnItemClickListener: Interface to handle item click events in the RecyclerView.
    interface OnItemClickListener {
        fun onItemClick(restaurantName: String)
    }

    // ViewHolder: Holds the views for each item in the RecyclerView.
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(restaurantName: String) {
            val restaurantNameTextView = itemView.findViewById<TextView>(R.id.restaurantNameTextView)
            restaurantNameTextView.text = restaurantName
            itemView.setOnClickListener { itemClickListener.onItemClick(restaurantName) }
        }
    }

    // onCreateViewHolder: Creates a new ViewHolder instance for each item in the RecyclerView.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_all_restaurant, parent, false)
        return ViewHolder(view)
    }

    // onBindViewHolder: Binds data to the ViewHolder for a specific item in the RecyclerView.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(allRestaurantList[position])
    }

    // getItemCount: Returns the total number of items in the RecyclerView.
    override fun getItemCount(): Int {
        return allRestaurantList.size
    }
}
