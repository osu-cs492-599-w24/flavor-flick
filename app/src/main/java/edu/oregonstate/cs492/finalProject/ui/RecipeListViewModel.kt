import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.oregonstate.cs492.finalProject.data.AppDatabase
import edu.oregonstate.cs492.finalProject.data.RecipeInfo
import edu.oregonstate.cs492.finalProject.data.RecipeInfoRepository
import kotlinx.coroutines.launch


class RecipeListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RecipeInfoRepository(
        AppDatabase.getInstance(application).recipeInfoDao()
    )

    val swipedRecipe = repository.getAllRecentRecipe()

    fun addRecipe(repo: RecipeInfo) {
        viewModelScope.launch {
            repository.insertRecipe(repo)
        }
    }
}