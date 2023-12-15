package evtomak.iu.edu.fooddelivery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AllRestaurantsAdapter(
    private val allRestaurantList: List<String>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<AllRestaurantsAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(restaurantName: String)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(restaurantName: String) {
            val restaurantNameTextView = itemView.findViewById<TextView>(R.id.restaurantNameTextView)
            restaurantNameTextView.text = restaurantName
            itemView.setOnClickListener { itemClickListener.onItemClick(restaurantName) }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_all_restaurant, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(allRestaurantList[position])
    }

    override fun getItemCount(): Int {
        return allRestaurantList.size
    }
}
