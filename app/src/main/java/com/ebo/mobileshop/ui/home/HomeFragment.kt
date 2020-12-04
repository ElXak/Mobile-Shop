package com.ebo.mobileshop.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ebo.mobileshop.*
import com.ebo.mobileshop.vo.SelectedBanner
import com.ebo.mobileshop.vo.SelectedSection
import com.ebo.mobileshop.vo.SelectedItem
import com.ebo.mobileshop.databinding.FragmentHomeBinding
import com.ebo.mobileshop.ui.shared.BannerViewModel
import com.ebo.mobileshop.ui.shared.SectionViewModel
import com.ebo.mobileshop.ui.shared.ItemViewModel

// Fragment is a User Interface and is only responsible for managing the presentation
class HomeFragment : Fragment(),
    // implementation of interface from RecyclerAdapter for listening of Recycler Item click
    SectionsRecyclerAdapter.ItemListener,
    BannersRecyclerAdapter.ItemListener,
    ItemsRecyclerAdapter.ItemListener{

    private lateinit var sectionViewModel: SectionViewModel
    private lateinit var sectionsRecyclerView: RecyclerView
    private lateinit var sectionsAdapter: SectionsRecyclerAdapter
    private lateinit var bannerViewModel: BannerViewModel
    private lateinit var bannersRecyclerView: RecyclerView
    private lateinit var bannersAdapter: BannersRecyclerAdapter
    private lateinit var itemViewModel: ItemViewModel
    private lateinit var itemsRecyclerView: RecyclerView
    private lateinit var itemsAdapter: ItemsRecyclerAdapter
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
        sectionViewModel =
                ViewModelProvider(requireActivity()).get(SectionViewModel::class.java)
        bannerViewModel =
                ViewModelProvider(this).get(BannerViewModel::class.java)
        itemViewModel =
                ViewModelProvider(requireActivity()).get(ItemViewModel::class.java)

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
            sectionViewModel.refreshData()
            bannerViewModel.refreshData()
            itemViewModel.refreshData()
        }

        sectionsRecyclerView = binding.sectionsRecycler
        bannersRecyclerView = binding.bannersRecycler
        itemsRecyclerView = binding.itemsRecycler

        bannersRecyclerView.isNestedScrollingEnabled = false
        bannersRecyclerView.setHasFixedSize(false)
        itemsRecyclerView.isNestedScrollingEnabled = false
        itemsRecyclerView.setHasFixedSize(false)

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
        sectionViewModel.data.observe(viewLifecycleOwner, {
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
            sectionsAdapter = SectionsRecyclerAdapter(requireContext(), it, this)
            // assign adapter to the recycler
            sectionsRecyclerView.adapter = sectionsAdapter

            // after receiving data hides refreshing icon on display
            swipeLayout.isRefreshing = false
        })

        bannerViewModel.data.observe(viewLifecycleOwner, {
            bannersAdapter = BannersRecyclerAdapter(requireContext(), it, this)
            bannersRecyclerView.adapter = bannersAdapter

            // after receiving data hides refreshing icon on display
            swipeLayout.isRefreshing = false
        })

        itemViewModel.data.observe(viewLifecycleOwner, {
            itemsAdapter = ItemsRecyclerAdapter(requireContext(), it, this)
            itemsRecyclerView.adapter = itemsAdapter

            // after receiving data hides refreshing icon on display
            swipeLayout.isRefreshing = false
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSectionItemClick(section: SelectedSection) {
        // pass selected section to the LiveData for observing it in SectionFragment
        sectionViewModel.selectData(section)
        // navigates to the destination of action in navigation element
        navController.navigate(R.id.nav_section)
    }

    override fun onBannerItemClick(banner: SelectedBanner) {
        navController.navigate(R.id.nav_section)
    }

    override fun onItemClick(item: SelectedItem) {
        // pass selected category to the LiveData for observing it in CategoryFragment
//        itemViewModel.selectData(item)
/*
        val bundle = bundleOf("id" to item.id)
        // navigates to the destination of action in navigation element
        navController.navigate(R.id.action_nav_product, bundle)
*/

        val intent = Intent(requireActivity(), ItemActivity::class.java)
        intent.action = Intent.ACTION_VIEW
        intent.putExtra(ITEM_ID, item.id)
        intent.putExtra(SECTION_ID, item.sectionId)
        startActivity(intent)
    }

}