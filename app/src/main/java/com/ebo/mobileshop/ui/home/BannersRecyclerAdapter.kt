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
import com.ebo.mobileshop.data.banner.SelectedBanner

class BannersRecyclerAdapter(
    val context: Context,
    val banners: List<SelectedBanner>,
    val itemListener: ItemListener
) :
    RecyclerView.Adapter<BannersRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val backgroundImage: ImageView = itemView.findViewById(R.id.background_image)
        val bannerText: TextView = itemView.findViewById(R.id.banner_text)
    }

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
        val view = inflater.inflate(R.layout.item_banners, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val banner = banners[position]

        with(holder) {
            Glide.with(context)
                .load(banner.pictureUrl)
                .into(backgroundImage)

            bannerText.let {
                it.text = banner.text
                it.contentDescription = banner.name
            }

            holder.itemView.setOnClickListener {
                itemListener.onBannerItemClick(banner)
            }
        }
    }

    override fun getItemCount() = banners.size

    interface ItemListener {
        fun onBannerItemClick(banner: SelectedBanner)
    }
}