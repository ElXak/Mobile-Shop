package com.ebo.mobileshop.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        categoryRecyclerView = binding.categoriesRecycler
        bannerRecyclerView = binding.bannersRecycler
        productRecyclerView = binding.productsRecycler

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

}