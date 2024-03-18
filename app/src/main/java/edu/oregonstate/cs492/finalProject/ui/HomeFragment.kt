package edu.oregonstate.cs492.finalProject.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import edu.oregonstate.cs492.finalProject.R
import edu.oregonstate.cs492.finalProject.data.ForecastPeriod
import edu.oregonstate.cs492.finalProject.data.RecipeItem
import edu.oregonstate.cs492.finalProject.util.openWeatherEpochToDate

/**
 * This fragment represents the "current weather" screen.
 */
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModels()
    private val homeAdapter = HomeAdapter()

    private lateinit var coordinatorLayout: View
    private lateinit var recipeListRV: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ChatGPT
        coordinatorLayout = requireActivity().findViewById(R.id.coordinator_layout)

        // set up recyclerview
        recipeListRV = view.findViewById(R.id.rv_recipe_list)
        recipeListRV.layoutManager = LinearLayoutManager(requireContext())
        recipeListRV.setHasFixedSize(true)
        recipeListRV.adapter = homeAdapter

        // https://stackoverflow.com/questions/30531091/how-to-disable-recyclerview-scrolling
        recipeListRV.layoutManager = object : LinearLayoutManager(requireContext()) { override fun canScrollVertically() = false }

        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                if (direction == ItemTouchHelper.LEFT){
                    val deletedRecipe = homeAdapter.deleteRecipe(position)
                    val snackbar = Snackbar.make(
                        coordinatorLayout,
                        "Disliked: ${deletedRecipe.strMeal}",
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.show()
                }
                else if (direction == ItemTouchHelper.RIGHT){
                    val deletedRecipe = homeAdapter.deleteRecipe(position)
                    val snackbar = Snackbar.make(
                        coordinatorLayout,
                        "Liked: ${deletedRecipe.strMeal}",
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.show()
                }


            }

        }

        // Test Items
        homeAdapter.createRecipe(
            RecipeItem("Jamaican Beef Patties", "https://www.themealdb.com/images/media/meals/wsqqsw1515364068.jpg", "Beef", "Jamaican","https://www.youtube.com/watch?v=ypQjoiZiTac","https://www.thespruce.com/jamaican-beef-patties-recipe-2137762")
        )
        homeAdapter.createRecipe(
            RecipeItem("Beef Patties", "https://www.themealdb.com/images/media/meals/wsqqsw1515364068.jpg", "Beef", "Jamaican","https://www.youtube.com/watch?v=ypQjoiZiTac","https://www.thespruce.com/jamaican-beef-patties-recipe-2137762")
        )
        homeAdapter.createRecipe(
            RecipeItem("Jamaican Beef Patties", "https://www.themealdb.com/images/media/meals/wsqqsw1515364068.jpg", "Beef", "Jamaican","https://www.youtube.com/watch?v=ypQjoiZiTac","https://www.thespruce.com/jamaican-beef-patties-recipe-2137762")
        )
        homeAdapter.createRecipe(
            RecipeItem("Jamaican Beef Patties", "https://www.themealdb.com/images/media/meals/wsqqsw1515364068.jpg", "Beef", "Jamaican","https://www.youtube.com/watch?v=ypQjoiZiTac","https://www.thespruce.com/jamaican-beef-patties-recipe-2137762")
        )
        homeAdapter.createRecipe(
            RecipeItem("Jamaican Beef Patties", "https://www.themealdb.com/images/media/meals/wsqqsw1515364068.jpg", "Beef", "Jamaican","https://www.youtube.com/watch?v=ypQjoiZiTac","https://www.thespruce.com/jamaican-beef-patties-recipe-2137762")
        )

        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(recipeListRV)


    }

//    override fun onResume() {
//        super.onResume()

        /*
         * Trigger loading the weather data as soon as the fragment resumes.  Doing this in
         * onResume() allows us to potentially refresh the forecast if the user navigates back
         * to the app after being away (e.g. if they updated the settings).
         *
         * Here, the OpenWeather API key is taken from the app's string resources.  See the
         * comment at the top of the main activity class to see how to make this work correctly.
         */
//        val city = prefs.getString(getString(R.string.pref_city_key), "Corvallis,OR,US")
//        val units = prefs.getString(
//            getString(R.string.pref_units_key),
//            getString(R.string.pref_units_default_value)
//        )
//        viewModel.loadCurrentWeather(city, units, getString(R.string.openweather_api_key))
//
//        val nonNullCity: String = city ?: ""

//        val bookmarkedRepo = GitHubRepo(cityName = nonNullCity, timeStamp = System.currentTimeMillis())
//    }

    /*
     * This function binds weather data from a ForecastPeriod object into the UI for this fragment.
     */
//    private fun bind(weather: ForecastPeriod) {
//        Glide.with(this)
//            .load(weather.iconUrl)
//            .into(requireView().findViewById(R.id.iv_icon))

//        val city = prefs.getString(getString(R.string.pref_city_key), "Corvallis,OR,US")
//        cityTV.text = city
//
//        /*
//         * Set the date text, adjusting based on timezone info available in the weather data.
//         */
//        dateTV.text = getString(
//            R.string.forecast_date_time,
//            openWeatherEpochToDate(weather.epoch, weather.tzOffsetSec)
//        )
//
//        tempTV.text = getString(R.string.forecast_temp, weather.temp)

        /*
         * Insert the text for the cloud cover, then add a cloud icon whose size is adapted to the
         * height of the text in the TextView.
         */
//        cloudsTV.text = getString(R.string.forecast_clouds, weather.cloudCover)
//        val cloudsIcon = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_outline_cloud_24)
//        val cloudsIconSize = (cloudsTV.lineHeight * 0.75).toInt()
//        cloudsIcon?.setBounds(0, 0, cloudsIconSize, cloudsIconSize)
//        cloudsTV.setCompoundDrawables(cloudsIcon, null, null, null)

        /*
         * Insert the text for the wind, then add a wind icon whose size is adapted to the height
         * of the text in the TextView.
         */
//        windTV.text = getString(
//            R.string.forecast_wind,
//            weather.windSpeed,
//            ""
//        )
////        val windIcon = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_baseline_air_24)
//        val windIconSize = (windTV.lineHeight * 0.75).toInt()
////        windIcon?.setBounds(0, 0, windIconSize, windIconSize)
////        windTV.setCompoundDrawables(windIcon, null, null, null)
//
//        /*
//         * Rotate the wind direction icon to indicate the direction of the wind.
//         */
//        windDirIV.rotation = weather.windDirDeg.toFloat()
//
//        descriptionTV.text = weather.description
}

