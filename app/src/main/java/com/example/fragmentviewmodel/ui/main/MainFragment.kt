package com.example.fragmentviewmodel.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fragmentviewmodel.R
import java.util.HashMap

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var myViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        var listDataChild: HashMap<String, List<String>>
        myViewModel.getData()
        myViewModel.getComments()
            .observe(viewLifecycleOwner, { map ->
                print("Find comments change")
                listDataChild = map

                var adapter =
                    CustomExpandableListAdapter(requireContext(), myViewModel.getNotes().value!!, listDataChild)
                if (binding.myListView.getExpandableListAdapter() == null) {
                    binding.myListView.setAdapter(adapter)
                }
                adapter.notifyDataSetChanged()
            })
        binding.buttonSecond.setOnClickListener {
            myViewModel.getData()
            // findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}