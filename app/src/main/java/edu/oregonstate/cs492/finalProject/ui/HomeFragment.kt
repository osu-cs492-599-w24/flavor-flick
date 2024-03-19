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
import kotlinx.coroutines.runBlocking

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
                    viewModel.fetchNewRecipe()

                    val snackbar = Snackbar.make(
                        coordinatorLayout,
                        "Disliked: ${deletedRecipe.name}",
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.show()
                }
                else if (direction == ItemTouchHelper.RIGHT){
                    val deletedRecipe = homeAdapter.deleteRecipe(position)
                    viewModel.fetchNewRecipe()

                    val snackbar = Snackbar.make(
                        coordinatorLayout,
                        "Liked: ${deletedRecipe.name}",
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.show()
                }
            }
        }

        /**
         * Ensure that when the recipe LiveData in HomeViewModel changes
         * we actually add it to recipeList in HomeAdapter
         * (which happens when viewModel.fetchNewRecipe() is called)
         */
        viewModel.recipe.observe(viewLifecycleOwner) { recipe ->
            recipe?.let {
                // Add the new recipe to the adapter
                homeAdapter.createRecipe(recipe)

            }
        }

        /**
         * Add a dummy recipe as the very first in the RecyclerView
         * Note: (it is hard to make this an API-random recipe, I couldn't (sob))
         * Note note: Could also make this a little info page about FlavorFlick
         *            that you can swipe away (because we don't have any info elsewhere)
         */
        homeAdapter.createRecipe(
            RecipeItem("Jamaican Beef Patties", "https://www.themealdb.com/images/media/meals/wsqqsw1515364068.jpg", "Beef", "Jamaican","https://www.youtube.com/watch?v=ypQjoiZiTac","https://www.thespruce.com/jamaican-beef-patties-recipe-2137762")
        )

        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(recipeListRV)
    }
}

