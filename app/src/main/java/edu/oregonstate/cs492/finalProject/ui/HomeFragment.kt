package edu.oregonstate.cs492.finalProject.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.oregonstate.cs492.finalProject.R
import kotlinx.coroutines.launch


class HomeFragment : Fragment(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModels()
    private val viewModelz: RecipeInfoViewModel by viewModels()

    private lateinit var coordinatorLayout: View
    private lateinit var recipeListRV: RecyclerView
    private lateinit var loadingErrorTV: TextView
    private lateinit var loadingIndicator: ProgressBar

    // Initialize HomeAdapter (with no recipes- it's empty for now)
    private var homeAdapter = HomeAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView and adapter
        homeAdapter = HomeAdapter()
        recipeListRV = view.findViewById(R.id.rv_recipe_list)
        recipeListRV.layoutManager = LinearLayoutManager(requireContext())
        recipeListRV.adapter = homeAdapter

        loadingErrorTV = view.findViewById(R.id.tv_loading_error)
        loadingIndicator = view.findViewById(R.id.loading_indicator)

        // Initialize HomeAdapter again with 5 initial recipes fetched from the API
        lifecycleScope.launch {
            val initialRecipes = viewModel.fetchInitialRecipes()
            homeAdapter = HomeAdapter(initialRecipes)
            recipeListRV.adapter = homeAdapter
        }

        // ChatGPT
        coordinatorLayout = requireActivity().findViewById(R.id.coordinator_layout)

        // set up recyclerview
        recipeListRV = view.findViewById(R.id.rv_recipe_list)
        recipeListRV.layoutManager = LinearLayoutManager(requireContext())
        recipeListRV.setHasFixedSize(true)
        recipeListRV.adapter = homeAdapter

        // https://stackoverflow.com/questions/30531091/how-to-disable-recyclerview-scrolling
        recipeListRV.layoutManager = object : LinearLayoutManager(requireContext()) { override fun canScrollVertically() = false }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                // Log the error or handle it as needed
                Log.e("HomeFragment", "Error: $error")
            }
        }
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

                }
                else if (direction == ItemTouchHelper.RIGHT){
                    val recipe = homeAdapter.recipeList[position] // Get the swiped recipe
                    val recipeName = recipe.name
                    val recipeImage = recipe.image
                    val recipeCategory = recipe.category
                    val recipeRegion = recipe.region
                    val recipeVideoLink = recipe.videoLink
                    val recipeRecipeLink = recipe.recipeLink

                    // Add all recipe details to the database
                    viewModelz.addNewRecipe(
                        recipeName,
                        recipeImage,
                        recipeCategory,
                        recipeRegion,
                        recipeVideoLink,
                        recipeRecipeLink
                    )

                    val deletedRecipe = homeAdapter.deleteRecipe(position)
                    viewModel.fetchNewRecipe()
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
         * NO LONGER NEEDED
         * THIS COMMENT IS LEFT AS A DINOSAUR FOSSIL
         *
         * Add a dummy recipe as the very first in the RecyclerView
         * Note: (it is hard to make this an API-random recipe, I couldn't (sob))
         * Note note: Could also make this a little info page about FlavorFlick
         *            that you can swipe away (because we don't have any info elsewhere)
         */
//        homeAdapter.createRecipe(
//            RecipeItem("Jamaican Beef Patties", "https://www.themealdb.com/images/media/meals/wsqqsw1515364068.jpg", "Beef", "Jamaican","https://www.youtube.com/watch?v=ypQjoiZiTac","https://www.thespruce.com/jamaican-beef-patties-recipe-2137762")
//        )

        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(recipeListRV)

        // visiblity for error
        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Log.e("HomeFragment", "Error: $error")
                loadingErrorTV.text = getString(R.string.loading_error, error.message)
                loadingErrorTV.visibility = View.VISIBLE
                error.printStackTrace()
            }
        }

        // visiblity for launch
        lifecycleScope.launch {
            try {
                loadingIndicator.visibility = View.VISIBLE
                val initialRecipes = viewModel.fetchInitialRecipes()
                loadingIndicator.visibility = View.GONE

                homeAdapter = HomeAdapter(initialRecipes)
                recipeListRV.adapter = homeAdapter

            } catch (e: Exception) {
                loadingIndicator.visibility = View.GONE
                loadingErrorTV.visibility = View.VISIBLE
                loadingErrorTV.text = getString(R.string.loading_error)
            }
        }
    }

    private fun hideRecipeList() {
        recipeListRV.visibility = View.GONE
    }
    override fun onPause() {
        super.onPause()
        hideRecipeList()
    }

    override fun onResume() {
        super.onResume()
        recipeListRV.visibility = View.VISIBLE
    }

}

