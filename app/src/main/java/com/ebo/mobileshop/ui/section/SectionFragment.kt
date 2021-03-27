package com.ebo.mobileshop.ui.section

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ebo.mobileshop.databinding.FragmentSectionBinding
import com.ebo.mobileshop.ui.shared.SectionViewModel

// TODO: 11/25/2020 sub sections at top of fragment and title of fragment also implement data binding in layout
class SectionFragment : Fragment() {

    private lateinit var viewModel: SectionViewModel
    private var _binding: FragmentSectionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(requireActivity()).get(SectionViewModel::class.java)

        _binding = FragmentSectionBinding.inflate(inflater, container, false)
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