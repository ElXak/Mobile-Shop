package com.ebo.mobileshop.utilities

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.NumberFormat

// keep all binding adapters in one file

// simple text values can be displayed with data binding expressions in the layout file
// more complex assignments thou might require use of adapter functions
// this are top level functions that are associated with your layout file with property names that you assign

// annotation of BinderAdapter passing the name of attribute that you want to use to assign value for layout file
@BindingAdapter("price")
fun itemPrice(view: TextView, value: Double) {
    // formats number as currency
    val formatter = NumberFormat.getCurrencyInstance()
    // format is applied to value
    val text = formatter.format(value)
    view.text = text
}