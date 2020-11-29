package com.ebo.mobileshop

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.ebo.mobileshop.data.product.SelectedProduct
import com.ebo.mobileshop.ui.product.SectionsPagerAdapter
import com.ebo.mobileshop.databinding.ActivityProductBinding
import com.ebo.mobileshop.ui.shared.ProductViewModel

class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding
    private lateinit var viewModel: ProductViewModel
//    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        navController = findNavController(R.id.nav_host)

        viewModel =
            ViewModelProvider(this).get(ProductViewModel::class.java)

        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = binding.fab

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val name: TextView = binding.name
/*
        val id = arguments.getInt("id")
        name.text = id
*/

/*
        viewModel.selectedData.observe(this, {
            Log.i(TAG, "onCreate: ${it.name}")
            name.text = it.name
        })
*/
        val id = intent.extras!!.getInt("id")
        name.text = id.toString()
    }
}