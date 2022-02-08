package com.example.gmcaching

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.gmcaching.databinding.AddCacheFragmentBinding
import com.example.gmcaching.databinding.CachesListFragmentBinding


class AddCacheFragment : Fragment() {
    companion object {
        fun newInstance() = AddCacheFragment()
    }

    private lateinit var editWordView: EditText
    private var _binding: AddCacheFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddCacheFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSave.setOnClickListener {
            if (textfieldsAreFilled()) {
                val action = AddCacheFragmentDirections.actionAddCacheFragmentToDatabaseFragment(
                    null.toString(), null.toString(), null.toString(), false
                )
                findNavController().navigate(action)
            } else {
                val action = AddCacheFragmentDirections.actionAddCacheFragmentToDatabaseFragment(
                    binding.editName.text.toString(),
                    binding.editKoordinaten.text.toString(), binding.editKoordinaten.text.toString(), true)
                findNavController().navigate(action)


            }
        }
    }

    private fun textfieldsAreFilled(): Boolean {
        return TextUtils.isEmpty(binding.editName.text)||TextUtils.isEmpty(binding.editKoordinaten.text)

    }


}