package com.qverkk.touristrentafriend.ui.dashboard.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.activity.addCallback
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qverkk.touristrentafriend.R
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.*
import java.util.stream.Collectors

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        val searchText: AutoCompleteTextView = root.findViewById(R.id.auto_complete_dashboard_search)

        val countriesListView: RecyclerView = root.findViewById(R.id.list_dashboard_countries)

        val countries = Locale
            .getAvailableLocales()
            .toList()
            .stream()
            .map { it.getDisplayCountry(Locale.ENGLISH) }
            .filter { it.isNotEmpty() }
            .sorted()
            .distinct()
            .collect(Collectors.toList())

        val searchAdapter = ArrayAdapter<String>(
            context!!,
            android.R.layout.simple_spinner_item, countries
        )

        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager


        searchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        searchText.setAdapter(searchAdapter)

        searchText.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val itemAtPosition: String = p0?.getItemAtPosition(p2) as String

                for (i in 0..countries.size) {
                    if (itemAtPosition == countries[i]) {
                        countriesListView.scrollToPosition(i)
                        inputMethodManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                        return
                    }
                }
            }


        }
        val adapter = CountriesAdapter(countries, findNavController())
        countriesListView.adapter = adapter
        countriesListView.layoutManager = LinearLayoutManager(context)

        dashboardViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}