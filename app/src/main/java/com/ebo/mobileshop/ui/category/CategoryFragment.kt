package com.ebo.mobileshop.ui.category

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ebo.mobileshop.TAG
import com.ebo.mobileshop.databinding.FragmentCategoryBinding
import com.ebo.mobileshop.ui.shared.CategoryViewModel

// TODO: 11/25/2020 sub categories at top of fragment and title of fragment also implement data binding in layout
class CategoryFragment : Fragment() {

    private lateinit var viewModel: CategoryViewModel
    private var _binding: FragmentCategoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(requireActivity()).get(CategoryViewModel::class.java)

        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.categoryText
        viewModel.selectedData.observe(viewLifecycleOwner, {
            textView.text = it.name
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}