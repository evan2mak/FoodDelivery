package evtomak.iu.edu.fooddelivery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

// Adapter for displaying restaurant images in a RecyclerView.
class RestaurantImagesAdapter(private val images: List<Int>) :
    RecyclerView.Adapter<RestaurantImagesAdapter.ImageViewHolder>() {

    // Inner ViewHolder class for holding image views.
    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image)
    }

    // Inflate the item layout and create a ViewHolder for it.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant_image, parent, false)
        return ImageViewHolder(view)
    }

    // Load the image into the ImageView using Glide.
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(images[position]).into(holder.imageView)
    }

    override fun getItemCount() = images.size
}
