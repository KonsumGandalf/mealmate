package konsum.gandalf.mealmate.recipe.domain.repository

import konsum.gandalf.mealmate.recipe.domain.models.Area
import konsum.gandalf.mealmate.recipe.domain.models.Category
import konsum.gandalf.mealmate.recipe.domain.models.Recipe

interface IRecipeRepository {
    suspend fun getCategories(): List<Category>
    suspend fun getRandomRecipes(): List<Recipe>
    suspend fun getAreas(): List<Area>
    suspend fun filterRecipes(recipeName: String, filterAreas: List<Area>, filterCategories: List<Category>): List<Recipe>
    suspend fun updateRecipe(recipe: Recipe): Recipe
    suspend fun createRecipe(recipe: Recipe): Recipe
}
