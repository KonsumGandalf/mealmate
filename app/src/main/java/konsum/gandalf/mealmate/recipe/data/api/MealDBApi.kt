package konsum.gandalf.mealmate.recipe.data.api

import konsum.gandalf.mealmate.recipe.data.api.MealDBEndpoints.AREAS
import konsum.gandalf.mealmate.recipe.data.api.MealDBEndpoints.CATEGORIES
import konsum.gandalf.mealmate.recipe.data.api.MealDBEndpoints.RANDOM_RECIPE
import konsum.gandalf.mealmate.recipe.data.api.MealDBEndpoints.SEARCH
import konsum.gandalf.mealmate.recipe.data.api.models.RecipeResponse
import konsum.gandalf.mealmate.recipe.domain.models.Area
import konsum.gandalf.mealmate.recipe.domain.models.Category
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealDBApi {
    @GET(CATEGORIES)
    fun getCategories(): Call<Map<String, List<Category>>>

    @GET(RANDOM_RECIPE)
    fun getRandomRecipe(): Call<Map<String, List<RecipeResponse>>>

    @GET(AREAS)
    fun getAreas(): Call<Map<String, List<Area>>>

    @GET(SEARCH)
    fun filterRecipes(@Query("s") recipeName: String): Call<Map<String, List<RecipeResponse>>>
}
