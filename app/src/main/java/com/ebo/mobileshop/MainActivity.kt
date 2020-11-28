package com.ebo.mobileshop

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import com.ebo.mobileshop.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setSupportActionBar(binding.appBarMain.toolbar)

/*
        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
*/
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
//        val bottomNavView: BottomNavigationView = binding.appBarMain.contentMain.bottomNavView
        val bottomNavView: BottomNavigationView = binding.contentMain.bottomNavView
        navController = findNavController(R.id.nav_host)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, // TODO change gallery and slideshow
                R.id.nav_search, R.id.nav_cart, R.id.nav_profile // TODO add more
        ), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        bottomNavView.setupWithNavController(navController)
    }

/*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.top_notifications, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val messageMenuItem = menu?.findItem(R.id.top_messages)
        val messagesRootView = messageMenuItem?.actionView as FrameLayout
        messagesRedCircle = messagesRootView.findViewById(R.id.message_red_circle) as FrameLayout
        messagesCounter = messagesRootView.findViewById(R.id.message_counter_text) as TextView

        val messagesCount = 3.toString()
        messagesCounter.text = messagesCount
        if (messagesCount != "")
            messagesRedCircle.visibility = View.VISIBLE
        messagesRootView.setOnClickListener {
            onOptionsItemSelected(messageMenuItem)
        }

        val notificationMenuItem = menu.findItem(R.id.top_notifications)
        val notificationRootView = notificationMenuItem?.actionView as FrameLayout
        notificationsRedCircle = notificationRootView.findViewById(R.id.notification_red_circle) as FrameLayout
        notificationsCounter = notificationRootView.findViewById(R.id.notification_counter_text) as TextView

        val notificationsCount = 4.toString()
        notificationsCounter.text = notificationsCount
        if (notificationsCount != "")
            notificationsRedCircle.visibility = View.VISIBLE
        notificationRootView.setOnClickListener {
            onOptionsItemSelected(notificationMenuItem)
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.top_messages ->
                navController.navigate(R.id.nav_gallery)
            R.id.top_notifications ->
                navController.navigate(R.id.nav_slideshow)
        }
        return super.onOptionsItemSelected(item)
    }
*/

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}