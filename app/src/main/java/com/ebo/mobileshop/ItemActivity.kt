package com.ebo.mobileshop

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ebo.mobileshop.ui.item.SectionsPagerAdapter
import com.ebo.mobileshop.databinding.ActivityItemBinding
import com.ebo.mobileshop.ui.item.PageViewModel
import com.ebo.mobileshop.ui.item.SliderAdapter
import com.ebo.mobileshop.ui.item.ViewModelFactory
import com.smarteist.autoimageslider.SliderView

class ItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemBinding
    private lateinit var viewModel: PageViewModel

    private lateinit var cartRedCircle: FrameLayout
    private lateinit var cartCounter: TextView
//    private lateinit var navController: NavController

    // for usage outside of class as class object
    companion object {
        lateinit var params: Map<String, Int>
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        navController = findNavController(R.id.nav_host)

        val id = intent.extras!!.getInt(ITEM_ID)
        val sectionId = intent.extras!!.getInt(SECTION_ID)
        params = mapOf(Pair(ITEM_ID, id), Pair(SECTION_ID, sectionId))

        viewModel =
            // ViewModelFactory is used for passing arguments to the ViewModelClass
            ViewModelProvider(this, ViewModelFactory(this.application, params))
                .get(PageViewModel::class.java)

        binding = ActivityItemBinding.inflate(layoutInflater)
        binding.viewModel = viewModel

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        viewModel.data.observe(this, {
            Log.i(TAG, "onCreate: ${it}")
//            name.text = it.name
//            price.text = it.price.toString()
        })

        val slideView: SliderView = binding.imageSlider
//        val adapter = SliderAdapter(this,)

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

/*
        val name: TextView = binding.name
        val price: TextView = binding.price

        val id = arguments.getInt("id")
        name.text = id
*/

//        name.text = "id: ${id.toString()} sectionId: ${sectionId.toString()}"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_cart_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val cartMenuItem = menu?.findItem(R.id.top_cart)
        val cartRootView = cartMenuItem?.actionView as FrameLayout
        cartRedCircle = cartRootView.findViewById(R.id.view_cart_red_circle) as FrameLayout
        cartCounter = cartRootView.findViewById(R.id.view_cart_count_text) as TextView
        cartCounter.text = "3"
        cartRedCircle.visibility = View.VISIBLE
        cartRootView.setOnClickListener {
            onOptionsItemSelected(cartMenuItem)
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.top_cart -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.action = Intent.ACTION_VIEW
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}