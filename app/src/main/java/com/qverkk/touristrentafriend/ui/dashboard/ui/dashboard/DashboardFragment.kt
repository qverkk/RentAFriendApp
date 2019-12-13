package com.qverkk.touristrentafriend.ui.dashboard.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qverkk.touristrentafriend.R
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
        val countriesListView: RecyclerView = root.findViewById(R.id.list_dashboard_countries)

        val countries = Locale.getAvailableLocales().toList().stream().map { it.displayCountry }.filter { it.isNotEmpty() }.sorted()
            .distinct().collect(Collectors.toList())

        val adapter = CountriesAdapter(countries, findNavController())
        countriesListView.adapter = adapter
        countriesListView.layoutManager = LinearLayoutManager(context)

        dashboardViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}