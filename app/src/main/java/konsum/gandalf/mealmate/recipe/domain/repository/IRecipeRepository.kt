package konsum.gandalf.mealmate.recipe.domain.repository

import konsum.gandalf.mealmate.recipe.data.api.models.AreaResponse
import konsum.gandalf.mealmate.recipe.data.api.models.CategoryResponse
import konsum.gandalf.mealmate.recipe.domain.models.Recipe

interface IRecipeRepository {
    suspend fun getCategories(): List<CategoryResponse>
    suspend fun getRandomRecipes(): List<Recipe>
    suspend fun getAreas(): List<AreaResponse>
    suspend fun filterRecipes(recipeName: String, filterAreas: List<AreaResponse>, filterCategories: List<CategoryResponse>): List<Recipe>
}
