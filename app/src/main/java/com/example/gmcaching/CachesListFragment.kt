package com.example.gmcaching
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gmcaching.databinding.CachesListFragmentBinding


class CachesListFragment : Fragment() {

//    companion object {
//        fun newInstance() = CachesListFragment()
//    }
//
//    //private lateinit var viewModel: CachesListViewModel
//    private var _binding: CachesListFragmentBinding? = null
//    private val binding get() = _binding!!
//    private lateinit var recyclerView: RecyclerView
//    private val myDataset = Datasource().loadcaches()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = CachesListFragmentBinding.inflate(inflater, container, false)
//        val view = binding.root
//        return view
//    }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        recyclerView = binding.recyclerView
//        recyclerView.adapter = ItemAdapter(this.requireContext(), myDataset)
//
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//


}