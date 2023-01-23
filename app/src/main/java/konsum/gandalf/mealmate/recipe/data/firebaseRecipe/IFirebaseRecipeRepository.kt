package konsum.gandalf.mealmate.recipe.data.firebaseRecipe

import konsum.gandalf.mealmate.recipe.domain.models.Recipe

interface IFirebaseRecipeRepository {
    suspend fun createRecipe(recipe: Recipe): Recipe
    suspend fun getRecipe(recipeId: String): Recipe?
    suspend fun updateRecipe(recipe: Recipe): Recipe
    suspend fun deleteRecipe(id: String)
    suspend fun getRecipeByFilter(name: String): List<Recipe>
}
