package konsum.gandalf.mealmate.recipe.data.api

import konsum.gandalf.mealmate.recipe.data.api.MealDBEndpoints.AREAS
import konsum.gandalf.mealmate.recipe.data.api.MealDBEndpoints.CATEGORIES
import konsum.gandalf.mealmate.recipe.data.api.MealDBEndpoints.RANDOM_RECIPE
import konsum.gandalf.mealmate.recipe.data.api.models.AreaResponse
import konsum.gandalf.mealmate.recipe.data.api.models.CategoryResponse
import konsum.gandalf.mealmate.recipe.data.api.models.RecipeResponse
import retrofit2.Call
import retrofit2.http.GET

interface MealDBApi {
    @GET(CATEGORIES)
    fun getCategories(): Call<Map<String, List<CategoryResponse>>>

    @GET(RANDOM_RECIPE)
    fun getRandomRecipe(): Call<Map<String, List<RecipeResponse>>>

    @GET(AREAS)
    fun getAreas(): Call<Map<String, List<AreaResponse>>>
}
