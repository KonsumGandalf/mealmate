package konsum.gandalf.mealmate.recipe.data.repository

import konsum.gandalf.mealmate.recipe.data.api.MealDBApi
import konsum.gandalf.mealmate.recipe.data.api.models.AreaResponse
import konsum.gandalf.mealmate.recipe.data.api.models.CategoryResponse
import konsum.gandalf.mealmate.recipe.data.api.models.RecipeResponse
import konsum.gandalf.mealmate.recipe.domain.models.Ingredient
import konsum.gandalf.mealmate.recipe.domain.models.Recipe
import konsum.gandalf.mealmate.recipe.domain.repository.IRecipeRepository
import retrofit2.await
import javax.inject.Inject

class RecipeRepositoryImpl
@Inject
constructor(
    private val mealDb: MealDBApi
) : IRecipeRepository {

    override suspend fun getCategories(): List<CategoryResponse> {
        val response = mealDb.getCategories().await()
        return response["categories"] as List<CategoryResponse>
    }

    override suspend fun getAreas(): List<AreaResponse> {
        val response = mealDb.getAreas().await()
        return response["meals"] as List<AreaResponse>
    }

    override suspend fun getRandomRecipes(): List<Recipe> {
        val randomRecipeList = ArrayList<Recipe>()
        while (randomRecipeList.size < 4) {
            val response = mealDb.getRandomRecipe().await()
            val randomRecipeResponse = (response["meals"] as List<RecipeResponse>)[0]
            randomRecipeList.add(recipeResponseToRecipe(randomRecipeResponse))
        }
        return randomRecipeList
    }

    override suspend fun filterRecipes(
        recipeName: String,
        filterAreas: List<AreaResponse>,
        filterCategories: List<CategoryResponse>
    ): List<Recipe> {
        val response = mealDb.filterRecipes(recipeName).await()
        var filteredResponseList = response["meals"] as List<RecipeResponse>
        if (filterAreas.isNotEmpty()) {
            filteredResponseList = filteredResponseList.filter { recipe -> filterAreas.any { it.name == recipe.area } }
        }
        if (filterCategories.isNotEmpty()) {
            filteredResponseList = filteredResponseList.filter { recipe -> filterCategories.any { it.name == recipe.category } }
        }
        return filteredResponseList.map { recipeResponseToRecipe(it) }.toList()
    }

    private fun recipeResponseToRecipe(response: RecipeResponse): Recipe {
        var splitList: List<String> = listOf()
        try {
            splitList = response.tags.split(",")
        } catch (_: Exception) {}

        return Recipe(
            recipeId = response.recipeId,
            title = response.title,
            category = response.category,
            area = response.area,
            instructions = response.instructions,
            imageUrl = response.imageUrl,
            tags = splitList,
            ingredients = ingredientResponseToIngredients(response)
        )
    }

    private fun ingredientResponseToIngredients(response: RecipeResponse): List<Ingredient> {
        return with(response) {
            val list = ArrayList<Ingredient>()
            if (!ingredient1.isNullOrBlank() && !measure1.isNullOrBlank()) {
                list.add(Ingredient(ingredient1, measure1))
            }
            if (!ingredient2.isNullOrBlank() && !measure2.isNullOrBlank()) {
                list.add(Ingredient(ingredient2, measure2))
            }
            if (!ingredient3.isNullOrBlank() && !measure3.isNullOrBlank()) {
                list.add(Ingredient(ingredient3, measure3))
            }
            if (!ingredient4.isNullOrBlank() && !measure4.isNullOrBlank()) {
                list.add(Ingredient(ingredient4, measure4))
            }
            if (!ingredient5.isNullOrBlank() && !measure5.isNullOrBlank()) {
                list.add(Ingredient(ingredient5, measure5))
            }
            if (!ingredient6.isNullOrBlank() && !measure6.isNullOrBlank()) {
                list.add(Ingredient(ingredient6, measure6))
            }
            if (!ingredient7.isNullOrBlank() && !measure7.isNullOrBlank()) {
                list.add(Ingredient(ingredient7, measure7))
            }
            if (!ingredient8.isNullOrBlank() && !measure8.isNullOrBlank()) {
                list.add(Ingredient(ingredient8, measure8))
            }
            if (!ingredient9.isNullOrBlank() && !measure9.isNullOrBlank()) {
                list.add(Ingredient(ingredient9, measure9))
            }
            if (!ingredient10.isNullOrBlank() && !measure10.isNullOrBlank()) {
                list.add(Ingredient(ingredient10, measure10))
            }
            if (!ingredient11.isNullOrBlank() && !measure11.isNullOrBlank()) {
                list.add(Ingredient(ingredient11, measure11))
            }
            if (!ingredient12.isNullOrBlank() && !measure12.isNullOrBlank()) {
                list.add(Ingredient(ingredient12, measure12))
            }
            if (!ingredient13.isNullOrBlank() && !measure13.isNullOrBlank()) {
                list.add(Ingredient(ingredient13, measure13))
            }
            if (!ingredient14.isNullOrBlank() && !measure14.isNullOrBlank()) {
                list.add(Ingredient(ingredient14, measure14))
            }
            if (!ingredient15.isNullOrBlank() && !measure15.isNullOrBlank()) {
                list.add(Ingredient(ingredient15, measure15))
            }
            list
        }
    }
}
