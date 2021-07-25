package com.example.fragmentviewmodel.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.fragmentviewmodel.R
import com.example.fragmentviewmodel.model.Note

class DetailsFragment: Fragment(){
    companion object {
        fun newInstance() = DetailsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view = inflater.inflate(R.layout.details_fragment, container, false)
        var listView = view.findViewById(R.id.detailsListView) as ListView
        var bundle = arguments
        var id = bundle!!.getString("id")
        var transactionDate = bundle!!.getString("transactionDate")
        var summary = bundle!!.getString("summary")
        var credit = bundle!!.getString("credit")
        var debit = bundle!!.getString("debit")
        var list = ArrayList<String>()
        list.add("id: " + id!!)
        list.add("transactionDate: " +transactionDate!!)
        list.add("summary: " + summary!!)
        list.add("debit: " + debit!!)
        list.add("credit: " + credit!!)
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, list)
        listView.adapter = adapter
        return view
    }
}