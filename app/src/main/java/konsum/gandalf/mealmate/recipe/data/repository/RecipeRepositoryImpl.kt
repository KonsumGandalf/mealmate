package konsum.gandalf.mealmate.recipe.data.repository

import javax.inject.Inject
import konsum.gandalf.mealmate.recipe.data.api.MealDBApi
import konsum.gandalf.mealmate.recipe.data.api.models.RecipeResponse
import konsum.gandalf.mealmate.recipe.data.firebaseRecipe.IFirebaseRecipeRepository
import konsum.gandalf.mealmate.recipe.domain.models.Area
import konsum.gandalf.mealmate.recipe.domain.models.Category
import konsum.gandalf.mealmate.recipe.domain.models.Ingredient
import konsum.gandalf.mealmate.recipe.domain.models.Recipe
import konsum.gandalf.mealmate.recipe.domain.repository.IRecipeRepository
import konsum.gandalf.mealmate.user.domain.models.User
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import retrofit2.await

class RecipeRepositoryImpl
@Inject
constructor(
    private val mealDb: MealDBApi,
    private val firebaseDb: IFirebaseRecipeRepository
) : IRecipeRepository {

    override suspend fun getCategories(): List<Category> {
        val response = mealDb.getCategories().await()
        return response["categories"] as List<Category>
    }

    override suspend fun getAreas(): List<Area> {
        val response = mealDb.getAreas().await()
        return response["meals"] as List<Area>
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
        filterAreas: List<Area>,
        filterCategories: List<Category>
    ): List<Recipe> = coroutineScope {
        var apiList: List<Recipe> = ArrayList<Recipe>()
        var firebaseList: List<Recipe> = ArrayList<Recipe>()
        awaitAll(
            async {
                val apiResponse: Map<String, List<RecipeResponse>> = mealDb.filterRecipes(recipeName).await()
                if (apiResponse.containsKey("meals")) {
                    try {
                        var filteredApiResponseList = apiResponse["meals"] as List<RecipeResponse>
                        if (filterAreas.isNotEmpty()) {
                            filteredApiResponseList = filteredApiResponseList.filter { recipe -> filterAreas.any { it.name == recipe.area } }
                        }
                        if (filterCategories.isNotEmpty()) {
                            filteredApiResponseList = filteredApiResponseList.filter { recipe -> filterCategories.any { it.name == recipe.category } }
                        }
                        apiList = filteredApiResponseList.map { recipeResponseToRecipe(it) }.toList()
                    } catch (_: java.lang.Exception) {
                    }
                }
            },
            async {
                firebaseList = firebaseDb.getRecipeByFilter(recipeName)
                if (filterAreas.isNotEmpty()) {
                    firebaseList = firebaseList.filter { recipe -> filterAreas.any { it.name == recipe.area } }
                }
                if (filterCategories.isNotEmpty()) {
                    firebaseList = firebaseList.filter { recipe -> filterCategories.any { it.name == recipe.category } }
                }
            }
        )
        apiList.plus(firebaseList)
    }

    override suspend fun updateRecipe(recipe: Recipe): Recipe {
        return firebaseDb.updateRecipe(recipe)
    }

    override suspend fun createRecipe(recipe: Recipe): Recipe {
        return firebaseDb.createRecipe(recipe)
    }

    override suspend fun deleteRecipe(recipeId: String) {
        firebaseDb.deleteRecipe(recipeId)
    }

    override suspend fun getUserRecipes(userId: String): List<Recipe> {
        return if (userId == mealDbUser.uid) {
            filterRecipes("", ArrayList<Area>(), ArrayList<Category>())
        } else {
            firebaseDb.getUserRecipes(userId)
        }
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
            ingredients = ingredientResponseToIngredients(response),
            owner = mealDbUser
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

    companion object {
        val mealDbUser = User(
            "Themealdb",
            "TheMealDB",
            "TheMealDB-1234567",
            imageUrl = "https://www.themealdb.com/images/meal-icon.png"
        )
    }
}
