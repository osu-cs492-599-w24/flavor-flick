package edu.oregonstate.cs492.finalProject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.oregonstate.cs492.finalProject.R
import edu.oregonstate.cs492.finalProject.data.RecipeItem

/**
 * This fragment represents a list of recipes.
 */
class RecipeListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Initialize and set up the RecyclerView here
//        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        recyclerView.adapter = HomeAdapter(/* pass your data here */)

        super.onViewCreated(view, savedInstanceState)

        val dummyRecipes = listOf(
            RecipeItem("Recipe 1", "", "", "", "", ""),
            RecipeItem("Recipe 2", "", "", "", "", ""),
            RecipeItem("Recipe 3", "", "", "", "", "")
        )


        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = RecipeListAdapter(dummyRecipes)

    }
}
