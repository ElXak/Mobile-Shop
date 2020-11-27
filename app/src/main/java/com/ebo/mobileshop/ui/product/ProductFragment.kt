package com.ebo.mobileshop.ui.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.ebo.mobileshop.R
import com.ebo.mobileshop.databinding.FragmentCategoryBinding
import com.ebo.mobileshop.databinding.FragmentProductBinding
import com.ebo.mobileshop.ui.shared.CategoryViewModel
import com.ebo.mobileshop.ui.shared.ProductViewModel

class ProductFragment : Fragment() {
    private lateinit var viewModel: ProductViewModel
    private var _binding: FragmentProductBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)

        _binding = FragmentProductBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.productText
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