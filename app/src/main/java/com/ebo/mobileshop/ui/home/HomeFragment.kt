package com.ebo.mobileshop.ui.home

import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ebo.mobileshop.R
import com.ebo.mobileshop.data.banner.SelectedBanner
import com.ebo.mobileshop.data.category.SelectedCategory
import com.ebo.mobileshop.data.product.SelectedProduct
import com.ebo.mobileshop.databinding.FragmentHomeBinding
import com.ebo.mobileshop.ui.shared.BannerViewModel
import com.ebo.mobileshop.ui.shared.CategoryViewModel
import com.ebo.mobileshop.ui.shared.ProductViewModel
import com.google.android.material.snackbar.Snackbar

// Fragment is a User Interface and is only responsible for managing the presentation
class HomeFragment : Fragment(),
    // implementation of interface from RecyclerAdapter for listening of Recycler Item click
    CategoriesRecyclerAdapter.ItemListener,
    BannersRecyclerAdapter.ItemListener,
    ProductsRecyclerAdapter.ItemListener{

    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var categoriesAdapter: CategoriesRecyclerAdapter
    private lateinit var bannerViewModel: BannerViewModel
    private lateinit var bannerRecyclerView: RecyclerView
    private lateinit var bannersAdapter: BannersRecyclerAdapter
    private lateinit var productViewModel: ProductViewModel
    private lateinit var productRecyclerView: RecyclerView
    private lateinit var productsAdapter: ProductsRecyclerAdapter
    private lateinit var swipeLayout: SwipeRefreshLayout
    private lateinit var navController: NavController
    private var _binding: FragmentHomeBinding? = null

    private lateinit var messagesRedCircle: FrameLayout
    private lateinit var messagesCounter: TextView
    private lateinit var notificationsRedCircle: FrameLayout
    private lateinit var notificationsCounter: TextView


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        categoryViewModel =
                ViewModelProvider(requireActivity()).get(CategoryViewModel::class.java)
        bannerViewModel =
                ViewModelProvider(this).get(BannerViewModel::class.java)
        productViewModel =
                ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // in order to get reference to ActionBar Component
        // we have to explicitly cast Activity as AppCompatActivity
        (requireActivity() as AppCompatActivity).run {
            // removes up button. it is member of ActionBar Component
//            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            setSupportActionBar(binding.toolbar)
        }

        // setHasOptionsMenu(true) is not needed in Activities
        // but in Fragment OptionsMenu doesn't work without setHasOptionsMenu(true)
        // it makes fragment to listen OptionsMenu actions
        setHasOptionsMenu(true)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        // passed requireActivity() because NavController is located in Activity rather then Fragment
        navController = Navigation.findNavController(
            requireActivity(), R.id.nav_host
        )

        (requireActivity() as AppCompatActivity).run {
            swipeLayout = findViewById(R.id.swipe_layout)
        }

//        swipeLayout = binding.swipeLayout
        // swipe event listener as a lambda expression that reacts on gesture
        swipeLayout.setOnRefreshListener {
            categoryViewModel.refreshData()
            bannerViewModel.refreshData()
            productViewModel.refreshData()
        }

        categoryRecyclerView = binding.contentHome.categoriesRecycler
        bannerRecyclerView = binding.contentHome.bannersRecycler
        productRecyclerView = binding.contentHome.productsRecycler

        bannerRecyclerView.isNestedScrollingEnabled = false
        bannerRecyclerView.setHasFixedSize(false)
        productRecyclerView.isNestedScrollingEnabled = false
        productRecyclerView.setHasFixedSize(false)

/*
        // get my layoutStyle from preferences. this code executed when fragment starts up
        val layoutStyle = PrefsHelper.getItemType(requireContext())
        recyclerView.layoutManager =
            if (layoutStyle == VIEW_TYPE_GRID) GridLayoutManager(requireContext(), 2)
            else LinearLayoutManager(requireContext())
*/

/*
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
*/
        categoryViewModel.data.observe(viewLifecycleOwner, {
/*
            val categoryNames = StringBuilder()
            for (category in it) {
                categoryNames.append(category.name).append("\n")
            }
            textView.text = categoryNames
*/

            // instance of adapter, passing context, observed viewModel.monsterData
            // and Fragment itself as a listener
            // adapter split to property declaration
            categoriesAdapter = CategoriesRecyclerAdapter(requireContext(), it, this)
            // assign adapter to the recycler
            categoryRecyclerView.adapter = categoriesAdapter

            // after receiving data hides refreshing icon on display
            swipeLayout.isRefreshing = false
        })

        bannerViewModel.data.observe(viewLifecycleOwner, {
            bannersAdapter = BannersRecyclerAdapter(requireContext(), it, this)
            bannerRecyclerView.adapter = bannersAdapter

            // after receiving data hides refreshing icon on display
            swipeLayout.isRefreshing = false
        })

        productViewModel.data.observe(viewLifecycleOwner, {
            productsAdapter = ProductsRecyclerAdapter(requireContext(), it, this)
            productRecyclerView.adapter = productsAdapter

            // after receiving data hides refreshing icon on display
            swipeLayout.isRefreshing = false
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCategoryItemClick(category: SelectedCategory) {
        // pass selected category to the LiveData for observing it in CategoryFragment
        categoryViewModel.selectData(category)
        // navigates to the destination of action in navigation element
        navController.navigate(R.id.nav_category)
    }

    override fun onBannerItemClick(banner: SelectedBanner) {
        navController.navigate(R.id.nav_category)
    }

    override fun onProductItemClick(product: SelectedProduct) {
        // pass selected category to the LiveData for observing it in CategoryFragment
        productViewModel.selectData(product)
        // navigates to the destination of action in navigation element
        navController.navigate(R.id.nav_product)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.top_notifications, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val messageMenuItem = menu.findItem(R.id.top_messages)
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

        super.onPrepareOptionsMenu(menu)
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

}