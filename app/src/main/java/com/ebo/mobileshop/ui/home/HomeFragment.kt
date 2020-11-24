package com.ebo.mobileshop.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ebo.mobileshop.R
import com.ebo.mobileshop.data.category.TopLevelCategory
import com.ebo.mobileshop.databinding.FragmentHomeBinding

// Fragment is a User Interface and is only responsible for managing the presentation
class HomeFragment : Fragment(),
    // implementation of interface from RecyclerAdapter for listening of Recycler Item click
    TopLevelCategoriesRecyclerAdapter.CategoryItemListener{

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var navController: NavController
    private lateinit var adapter: TopLevelCategoriesRecyclerAdapter
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // passed requireActivity() because NavController is located in Activity rather then Fragment
        navController = Navigation.findNavController(
            requireActivity(), R.id.nav_host_fragment_content_main
        )

        swipeRefreshLayout = binding.swipeLayout
        // swipe event listener as a lambda expression that reacts on gesture
        swipeRefreshLayout.setOnRefreshListener {
            homeViewModel.refreshData()
        }

        recyclerView = binding.categoriesRecyclerView

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
        homeViewModel.topLevelCategoryData.observe(viewLifecycleOwner, {
            val topLevelCategoryNames = StringBuilder()
            for (category in it) {
                topLevelCategoryNames.append(category.name).append("\n")
            }
//            textView.text = topLevelCategoryNames

            // instance of adapter, passing context, observed viewModel.monsterData
            // and Fragment itself as a listener
            // adapter split to property declaration
            adapter = TopLevelCategoriesRecyclerAdapter(requireContext(), it, this)
            // assign adapter to the recycler
            recyclerView.adapter = adapter

            // after receiving data hides refreshing icon on display
            swipeRefreshLayout.isRefreshing = false
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCategoryItemClick(category: TopLevelCategory) {
        // pass selected category to the LiveData for observing it in CategoryFragment
        homeViewModel._selectedCategory.value = category
        // navigates to the destination of action in navigation element
        navController.navigate(R.id.nav_gallery)

    }
}