package com.ebo.mobileshop.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ebo.mobileshop.R
import com.ebo.mobileshop.data.product.SelectedProduct
import java.text.NumberFormat

// Receives data and applies to each item in recycler view
// Generic notation is <MainRecyclerAdapter.ViewHolder>()>.
// ViewHolder() is inner class of adapter itself
class ProductsRecyclerAdapter(
    val context: Context,
    val products: List<SelectedProduct>,
    // register fragment as a listener
    val itemListener: ItemListener
):
    RecyclerView.Adapter<ProductsRecyclerAdapter.ViewHolder>() {

    // inner class because it is inside of other class
    //one of jobs of ViewHolder is to contain references to its child views
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val previewImage: ImageView = itemView.findViewById(R.id.preview_image)
        val nameText: TextView = itemView.findViewById(R.id.name_text)
        val priceText: TextView = itemView.findViewById(R.id.price_text)
        val ratingText: TextView = itemView.findViewById(R.id.rating_text)
    }

    // creates layout view, parent is the ViewGroup at the root of the Layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
/*
        // if you want to use multiple view types use this together with settings
        // find out from preferences what layout style supposed to use
        val layoutStyle = PrefsHelper.getItemType(parent.context)
        // if layoutStyle = VIEW_TYPE_GRID show grid layout else show list layout
        val layoutId = if (layoutStyle == VIEW_TYPE_GRID) {
            R.layout.monster_grid_item
        } else {
            R.layout.monster_list_item
        }
*/
        val view = inflater.inflate(R.layout.item_products, parent, false)

        return ViewHolder(view)
    }

    // binds data to the ViewHolder, holder reference passed in and will be called for each item in the grid
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // get single data out of the collection using the [position] index
        val product = products[position]
        // assign values to each of the objects in the holder
        // with(holder) for referencing holder multiple times
        with(holder) {
            // let for referencing multiple parameters of object
            nameText.let {
                it.text = product.name
                it.contentDescription = product.previewText
            }

            // formats number as currency
            val formatter = NumberFormat.getCurrencyInstance()
            // format is applied to value
            val text = formatter.format(product.price)
            priceText.text = text

            ratingText.text = product.rating.toString()
            // sets the image category.picture into categoryImage
            Glide.with(context)
                .load(product.pictureUrl)
                .into(previewImage)
            // handles event of clicking one of the items
            // itemView is the root element of the Layout
            holder.itemView.setOnClickListener {
                // sending selected item monster to the fragment
                itemListener.onProductItemClick(product)
            }
        }
    }

    // return value can be assigned to the fun
    override fun getItemCount() = products.size

    // click event is handled by RecyclerAdapter, but action is taken by activity or fragment
    // eventual action after clicking on recycler item must be taken by activity or fragment
    interface ItemListener {
        fun onProductItemClick(product: SelectedProduct)
    }

}