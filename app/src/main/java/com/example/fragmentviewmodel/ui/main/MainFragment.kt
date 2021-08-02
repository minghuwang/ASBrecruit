package com.example.fragmentviewmodel.ui.main

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import com.example.fragmentviewmodel.R
import com.example.fragmentviewmodel.model.Note
import com.example.fragmentviewmodel.utils.Utils

class MainFragment : Fragment(), SensorEventListener {
    companion object {
        fun newInstance() = MainFragment()
    }

    lateinit var sensorManager: SensorManager
    lateinit var presure: Sensor
    private lateinit var myViewModel: MainViewModel
    private lateinit var notes: List<Note>
    var listItems = ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String> //=  ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, listItems)
    private var steps = 0.0
    private var stepBase = 0.0
    private var setStepBase = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        myViewModel.InitData()
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, listItems)

        if (Utils.Pressure) {
            sensorManager = this.activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            presure = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
            if (presure == null) {
                println("No air presure sensor")
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view = inflater.inflate(R.layout.main_fragment, container, false)
        return view
    }

    override fun onSensorChanged(event: SensorEvent?) {

        var millibarsOfPressure = event?.values?.get(0)
        println("Sensor changed: ")
        println("$millibarsOfPressure")
        myViewModel.setPresure(millibarsOfPressure.toString())

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        println("onAccuracyChanged: ${sensor.toString()} $accuracy")
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, presure, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var listView = view.findViewById(R.id.myListView) as ListView

        var bundle = Bundle()
        if (Utils.Pressure) {
            myViewModel.getPresure().observe(viewLifecycleOwner, { pressure ->

//                val adapter =
//                    ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, listItems)
//
                if (listView.getAdapter() == null) {
                    listView.setAdapter(adapter)
                }
                if (!setStepBase) {

                    if (pressure.toDouble() > 0.0) {
                        setStepBase = true
                        stepBase = pressure.toDouble()
                        println("set StepBase: $stepBase")
                    }
                }
                steps = pressure.toDouble() - stepBase
                println("Steps: $steps")
                println("StepBase: $stepBase")
                listItems.clear()
                listItems.add(0, steps.toString())
                adapter.notifyDataSetChanged()
            })

        } else
            myViewModel.getNotes()
                .observe(viewLifecycleOwner, { listNotes: List<Note> ->
//                val adapter =
//                    ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, listItems)

                    notes = listNotes

                    notes.forEach {
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
                            var n = notes[position]

                            bundle.putString("id", n.id)
                            bundle.putString("transactionDate", n.transactionDate)
                            bundle.putString("summary", n.summary)
                            bundle.putString("credit", n.credit.toString())
                            bundle.putString("debit", n.debit.toString())

                            parentFragmentManager.commit {
                                setReorderingAllowed(true)
                                replace<DetailsFragment>(R.id.container, "Details", bundle)
                                addToBackStack("First fragment")
                            }
                        }
                })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}