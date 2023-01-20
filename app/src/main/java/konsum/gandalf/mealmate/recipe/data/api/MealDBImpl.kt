package konsum.gandalf.mealmate.recipe.data.api

import konsum.gandalf.mealmate.recipe.data.api.models.AreaResponse
import konsum.gandalf.mealmate.recipe.data.api.models.CategoryResponse
import konsum.gandalf.mealmate.recipe.data.api.models.RecipeResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import javax.inject.Inject

class MealDBImpl @Inject constructor(
    private val mealDBApi: MealDBApi
) : MealDBApi {

    override fun getCategories(): Call<Map<String, List<CategoryResponse>>> {
        return mealDBApi.getCategories()
    }

    override fun getRandomRecipe(): Call<Map<String, List<RecipeResponse>>> {
        return mealDBApi.getRandomRecipe()
    }

    override fun getAreas(): Call<Map<String, List<AreaResponse>>> {
        return mealDBApi.getAreas()
    }
}
