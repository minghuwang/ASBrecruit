package com.example.fragmentviewmodel.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fragmentviewmodel.R

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
        //var listItems: ArrayList<String>
        myViewModel.InitData()
        myViewModel.getNotes()
            .observe(viewLifecycleOwner, { map ->
                print("Find comments change")
                val adapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, map)
                var listView = view?.findViewById(R.id.myListView) as ListView
                listView.adapter = adapter
                if (listView.getAdapter() == null) {
                    listView.setAdapter(adapter)
                }
                adapter.notifyDataSetChanged()
            })

        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

}