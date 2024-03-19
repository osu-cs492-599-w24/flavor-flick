package edu.oregonstate.cs492.finalProject.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import edu.oregonstate.cs492.finalProject.R

class JournalFragment : Fragment(R.layout.add_journal_entries) {
    private val viewModel: JournalViewModel by viewModels()
    private lateinit var journalAdapter: JournalAdapter
    private lateinit var journalEntryTitle: EditText
    private lateinit var journalEntryEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        journalEntryTitle = view.findViewById(R.id.title_text_box)
        journalEntryEditText = view.findViewById(R.id.journal_text_box)
        saveButton = view.findViewById(R.id.save_button)
        cancelButton = view.findViewById(R.id.cancel_button)

        // only show buttons when the text box is clicked
        saveButton.visibility = View.GONE
        cancelButton.visibility = View.GONE

        journalEntryEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                saveButton.visibility = View.VISIBLE
                cancelButton.visibility = View.VISIBLE
            } else {
                saveButton.visibility = View.GONE
                cancelButton.visibility = View.GONE
            }
        }

        saveButton.setOnClickListener {
            val journalEntry = journalEntryEditText.text.toString()
            val journalTitle = journalEntryTitle.text.toString()
//            implement way to save journal entry
//            use database stuff that has been set up
//            viewModel.saveJournalEntry(journalEntry)
            journalEntryTitle.text.clear()
            journalEntryTitle.clearFocus()
            journalEntryEditText.text.clear()
            journalEntryEditText.clearFocus()
        }
        cancelButton.setOnClickListener {
            journalEntryTitle.text.clear()
            journalEntryTitle.clearFocus()
            journalEntryEditText.text.clear()
            journalEntryEditText.clearFocus()
            saveButton.visibility = View.GONE
            cancelButton.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
    }
}



//package edu.oregonstate.cs492.finalProject.ui
//
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.preference.PreferenceManager
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.google.android.material.progressindicator.CircularProgressIndicator
//import edu.oregonstate.cs492.finalProject.R
//
///**
// * This fragment represents the "five-day forecast" screen.
// */
//class JournalFragment: Fragment(R.layout.fragment_journal) {
//    private val viewModel: JournalViewModel by viewModels()
//    private val forecastAdapter = ForecastAdapter()
//
//    private lateinit var cityTV: TextView
//    private lateinit var forecastListRV: RecyclerView
//    private lateinit var loadingErrorTV: TextView
//    private lateinit var loadingIndicator: CircularProgressIndicator
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        cityTV = view.findViewById(R.id.tv_city)
//        loadingErrorTV = view.findViewById(R.id.tv_loading_error)
//        loadingIndicator = view.findViewById(R.id.loading_indicator)
//
//        /*
//         * Set up RecyclerView.
//         */
//        forecastListRV = view.findViewById(R.id.rv_forecast_list)
//        forecastListRV.layoutManager = LinearLayoutManager(requireContext())
//        forecastListRV.setHasFixedSize(true)
//        forecastListRV.adapter = forecastAdapter
//
//        /*
//         * Set up an observer on the current forecast data.  If the forecast is not null, display
//         * it in the UI.
//         */
//        viewModel.forecast.observe(viewLifecycleOwner) { forecast ->
//            if (forecast != null) {
//                forecastAdapter.updateForecast(forecast)
//                forecastListRV.visibility = View.VISIBLE
//                forecastListRV.scrollToPosition(0)
//            }
//        }
//
//        /*
//         * Set up an observer on the error associated with the current API call.  If the error is
//         * not null, display the error that occurred in the UI.
//         */
//        viewModel.error.observe(viewLifecycleOwner) { error ->
//            if (error != null) {
//                loadingErrorTV.text = getString(R.string.loading_error, error.message)
//                loadingErrorTV.visibility = View.VISIBLE
//                Log.e(tag, "Error fetching forecast: ${error.message}")
//                error.printStackTrace()
//            }
//        }
//
//        /*
//         * Set up an observer on the loading status of the API query.  Display the correct UI
//         * elements based on the current loading status.
//         */
//        viewModel.loading.observe(viewLifecycleOwner) { loading ->
//            if (loading) {
//                loadingIndicator.visibility = View.VISIBLE
//                loadingErrorTV.visibility = View.INVISIBLE
//                forecastListRV.visibility = View.INVISIBLE
//            } else {
//                loadingIndicator.visibility = View.INVISIBLE
//            }
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//
//        /*
//         * Trigger loading the forecast data as soon as the fragment resumes.  Doing this in
//         * onResume() allows us to potentially refresh the forecast if the user navigates back
//         * to the app after being away (e.g. if they updated the settings).
//         *
//         * Here, the OpenWeather API key is taken from the app's string resources.  See the
//         * comment at the top of the main activity class to see how to make this work correctly.
//         */
//        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
//        val city = prefs.getString(getString(R.string.pref_city_key), "Corvallis,OR,US")
//        val units = prefs.getString(
//            getString(R.string.pref_units_key),
//            getString(R.string.pref_units_default_value)
//        )
//        viewModel.loadFiveDayForecast(city, units, getString(R.string.openweather_api_key))
//        cityTV.text = city
//    }
//}