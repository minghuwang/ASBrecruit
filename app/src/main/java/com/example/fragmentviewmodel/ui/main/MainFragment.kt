package com.example.fragmentviewmodel.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fragmentviewmodel.R
import com.example.fragmentviewmodel.model.Note
import androidx.navigation.fragment.findNavController

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var myViewModel: MainViewModel
    private lateinit var notes: List<Note>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view = inflater.inflate(R.layout.main_fragment, container, false)
        myViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        myViewModel.InitData()

        var listItems = ArrayList<String>()

        var listView = view.findViewById(R.id.myListView) as ListView

        myViewModel.getNotes()
            .observe(viewLifecycleOwner, { listNotes: List<Note> ->
                val adapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, listItems)

                notes = listNotes
                notes.forEach{
//                    println(it.summary)
                    listItems.add(it.summary)
                }

                if (listView.getAdapter() == null) {
                    listView.setAdapter(adapter)
                }
                adapter.notifyDataSetChanged()
                listView.onItemClickListener =
                    AdapterView.OnItemClickListener { parent, view, position, id ->
                        //create a new fragment with bundle notes[position]
                    }
            })
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}