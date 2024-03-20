package edu.oregonstate.cs492.finalProject.ui

import RecipeListAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.oregonstate.cs492.finalProject.R
import edu.oregonstate.cs492.finalProject.data.RecipeInfo
import kotlinx.coroutines.launch

class RecipeListFragment : Fragment() {
    private val viewModel: RecipeInfoViewModel by viewModels()
    private lateinit var recipeListAdapter: RecipeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        val noRecipesTextView: TextView = view.findViewById(R.id.no_recipes_text_view)

        recipeListAdapter = RecipeListAdapter()
        recipeListAdapter.setDeleteRecipeListener(object: RecipeListAdapter.DeleteRecipeListener {
            override fun deleteRecipe(entry: RecipeInfo) {
                viewModel.viewModelScope.launch {
                    viewModel.deleteRecipe(entry)
                }
            }
        })


        viewModel.getAllEntries().observe(viewLifecycleOwner) { recipes ->
            val reversedRecipes = recipes.reversed()

            if (reversedRecipes.isEmpty()) {
                recyclerView.visibility = View.GONE
                noRecipesTextView.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                noRecipesTextView.visibility = View.GONE

                recipeListAdapter.submitList(reversedRecipes)
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = recipeListAdapter
            }
        }
    }
}
