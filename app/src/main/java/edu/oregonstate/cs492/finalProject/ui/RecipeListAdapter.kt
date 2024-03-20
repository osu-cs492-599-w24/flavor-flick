import android.content.Context
import android.graphics.Bitmap
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.oregonstate.cs492.finalProject.R
import edu.oregonstate.cs492.finalProject.data.RecipeInfo
import edu.oregonstate.cs492.finalProject.data.RecipeItem
import retrofit2.http.Tag

class RecipeListAdapter(
    private val initialRecipes: List<RecipeInfo> = listOf()
) : RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {

    private val recipeList: MutableList<RecipeInfo> = initialRecipes.toMutableList()
    private var deleteRecipeListener:  DeleteRecipeListener? = null

    fun submitList(newList: List<RecipeInfo>) {
        recipeList.clear()
        recipeList.addAll(newList)
    }

    interface DeleteRecipeListener{
        fun deleteRecipe(entry: RecipeInfo)
    }

    fun setDeleteRecipeListener(listener: DeleteRecipeListener){
        deleteRecipeListener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mealTV: TextView = itemView.findViewById(R.id.tv_name)
        private val mealThumbIV: ImageView = itemView.findViewById(R.id.tv_image)
        private val categoryTV: TextView = itemView.findViewById(R.id.tv_category)
        private val areaTV: TextView = itemView.findViewById(R.id.tv_region)
        private val youtubeTV: TextView = itemView.findViewById(R.id.tv_videoLink)
        private val sourceTV: TextView = itemView.findViewById(R.id.tv_recipeLink)
        private val generatePDFBtn: Button = itemView.findViewById(R.id.btnGeneratePDF)
        private val deleteRecipeButton: ImageButton = itemView.findViewById(R.id.recipe_delete_button)

        init {
            deleteRecipeButton.setOnClickListener {
                Log.d("Close Button", "Successfully clicked");
                val position = absoluteAdapterPosition
                Log.d("Close Button", "position: $position");
                if (position != RecyclerView.NO_POSITION) {
                    deleteRecipeListener?.deleteRecipe(recipeList[position])
                    recipeList.removeAt(position)
                    notifyItemRemoved(position)
                    Log.d("Close Button 2", "Successfully clicked again");
                }
            }
        }


        fun bind(recipeInfo: RecipeInfo) {
            val ctx = itemView.context

            mealTV.text = recipeInfo.title
            categoryTV.text = recipeInfo.category
            areaTV.text = recipeInfo.region

            // Setting YouTube Link text
            youtubeTV.apply {
                movementMethod = LinkMovementMethod.getInstance()
                text = HtmlCompat.fromHtml(
                    "<a href=\"${recipeInfo.videoLink}\">YouTube Link</a>",
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
            }

            // Setting Recipe Link text
            sourceTV.apply {
                movementMethod = LinkMovementMethod.getInstance()
                text = HtmlCompat.fromHtml(
                    "<a href=\"${recipeInfo.recipeLink}\">Recipe Link</a>",
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
            }

            Glide.with(ctx)
                .load(recipeInfo.image)
                .into(mealThumbIV)

            generatePDFBtn.setOnClickListener {
                val bitmap = mealThumbIV.drawable.toBitmap()
                PDFGenerator.generateRecipePdfAndOpen(recipeInfo, bitmap, ctx)
            }
        }
    }

    override fun getItemCount() = recipeList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_list_entry, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recipeList[position])
    }
}
