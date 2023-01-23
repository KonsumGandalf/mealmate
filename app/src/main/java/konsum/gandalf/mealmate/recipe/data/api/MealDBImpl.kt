package konsum.gandalf.mealmate.recipe.data.api

import konsum.gandalf.mealmate.recipe.data.api.models.RecipeResponse
import konsum.gandalf.mealmate.recipe.domain.models.Area
import konsum.gandalf.mealmate.recipe.domain.models.Category
import retrofit2.Call
import javax.inject.Inject

class MealDBImpl @Inject constructor(
    private val mealDBApi: MealDBApi
) : MealDBApi {

    override fun getCategories(): Call<Map<String, List<Category>>> {
        return mealDBApi.getCategories()
    }

    override fun getRandomRecipe(): Call<Map<String, List<RecipeResponse>>> {
        return mealDBApi.getRandomRecipe()
    }

    override fun getAreas(): Call<Map<String, List<Area>>> {
        return mealDBApi.getAreas()
    }

    override fun filterRecipes(recipeName: String): Call<Map<String, List<RecipeResponse>>> {
        return mealDBApi.filterRecipes(recipeName)
    }
}
