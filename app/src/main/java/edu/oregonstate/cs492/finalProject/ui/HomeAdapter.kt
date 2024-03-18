package edu.oregonstate.cs492.finalProject.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.oregonstate.cs492.finalProject.R
import edu.oregonstate.cs492.finalProject.data.RecipeItem

class HomeAdapter() : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    val recipeList: MutableList<RecipeItem> = mutableListOf()

    fun createRecipe(recipeItem: RecipeItem){
        recipeList.add(0, recipeItem)
        notifyItemInserted(0)
    }

    fun deleteRecipe(position: Int): RecipeItem {
        val recipeList = recipeList.removeAt(position)
        notifyItemRemoved(position)
        return recipeList
    }

    override fun getItemCount() = recipeList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        holder.bind(recipeList[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val mealTV: TextView = itemView.findViewById(R.id.tv_strMeal)
        private val mealThumbIV: ImageView = itemView.findViewById(R.id.iv_strMealThumb)
        private val categoryTV: TextView = itemView.findViewById(R.id.tv_strCategory)
        private val areaTV: TextView = itemView.findViewById(R.id.tv_strarea)
        private val youtubeTV: TextView = itemView.findViewById(R.id.tv_strYoutube)
        private val sourceTV: TextView = itemView.findViewById(R.id.tv_strSource)

        private lateinit var currentRandomRecipe: RecipeItem

        fun bind(recipeItem: RecipeItem,){
            currentRandomRecipe = recipeItem

            val ctx = itemView.context

            mealTV.text = ctx.getString(R.string.recipe_name, recipeItem.strMeal)
            categoryTV.text = ctx.getString(R.string.recipe_category, recipeItem.strCategory)
            areaTV.text = ctx.getString(R.string.recipe_area, recipeItem.strarea)
            youtubeTV.text = ctx.getString(R.string.recipe_youtube, recipeItem.strYoutube)
            sourceTV.text = ctx.getString(R.string.recipe_source, recipeItem.strSource)

            Glide.with(ctx)
                .load(recipeItem.strMealThumb)
                .into(mealThumbIV)
        }



    }


}