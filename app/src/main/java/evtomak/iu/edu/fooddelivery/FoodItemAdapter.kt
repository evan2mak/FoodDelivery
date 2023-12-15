package evtomak.iu.edu.fooddelivery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodItemAdapter(private val foodItems: MutableList<OrderItem>) :
    RecyclerView.Adapter<FoodItemAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodNameTextView: TextView = itemView.findViewById(R.id.tvFoodName)
        val priceTextView: TextView = itemView.findViewById(R.id.tvPrice)
        val quantityTextView: TextView = itemView.findViewById(R.id.tvQuantity)
        val incrementButton: Button = itemView.findViewById(R.id.btnIncrement)
        val decrementButton: Button = itemView.findViewById(R.id.btnDecrement)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foodItem = foodItems[position]
        holder.foodNameTextView.text = foodItem.foodName
        holder.priceTextView.text = String.format("$%.2f", foodItem.price)
        holder.quantityTextView.text = foodItem.quantity.toString()

        holder.incrementButton.setOnClickListener {
            foodItem.quantity++
            notifyItemChanged(position)
        }

        holder.decrementButton.setOnClickListener {
            if (foodItem.quantity > 0) {
                foodItem.quantity--
                notifyItemChanged(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return foodItems.size
    }
}
