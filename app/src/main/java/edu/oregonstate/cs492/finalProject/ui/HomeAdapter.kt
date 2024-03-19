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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mealTV: TextView = itemView.findViewById(R.id.tv_name)
        private val mealThumbIV: ImageView = itemView.findViewById(R.id.tv_image)
        private val categoryTV: TextView = itemView.findViewById(R.id.tv_category)
        private val areaTV: TextView = itemView.findViewById(R.id.tv_region)
        private val youtubeTV: TextView = itemView.findViewById(R.id.tv_videoLink)
        private val sourceTV: TextView = itemView.findViewById(R.id.tv_recipeLink)

        private lateinit var currentRandomRecipe: RecipeItem

        fun bind(recipeItem: RecipeItem,){
            currentRandomRecipe = recipeItem

            val ctx = itemView.context

            mealTV.text = ctx.getString(R.string.recipe_name, recipeItem.name)
            categoryTV.text = ctx.getString(R.string.recipe_category, recipeItem.category)
            areaTV.text = ctx.getString(R.string.recipe_area, recipeItem.region)
            youtubeTV.text = ctx.getString(R.string.recipe_youtube, recipeItem.videoLink)
            sourceTV.text = ctx.getString(R.string.recipe_source, recipeItem.recipeLink)

            Glide.with(ctx)
                .load(recipeItem.image)
                .into(mealThumbIV)
        }
    }
}