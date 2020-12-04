package com.ebo.mobileshop.ui.item

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ebo.mobileshop.R
import com.ebo.mobileshop.vo.Image
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter(
    private val context: Context,
    private val slides: List<Image>
    ) :
    SliderViewAdapter<SliderAdapter.SliderAdapterVH>() {

    inner class SliderAdapterVH(itemView: View) : SliderViewAdapter.ViewHolder(itemView) {

        val backgroundImage: ImageView = itemView.findViewById(R.id.background_image)
        val descriptionText: TextView = itemView.findViewById(R.id.description_text)

    }

    override fun getCount() = slides.size

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_slider, null)
        return SliderAdapterVH(inflater)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val slide = slides[position]

        with(viewHolder) {
            descriptionText.let {
                it.text = slide.text
                it.contentDescription = slide.text
            }

            Glide.with(context)
                .load(slide.imageUrl)
                .into(backgroundImage)
        }
    }

}