package konsum.gandalf.mealmate.recipe.ui.viewmodels

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import konsum.gandalf.mealmate.recipe.domain.models.Area
import konsum.gandalf.mealmate.recipe.domain.models.Category
import konsum.gandalf.mealmate.recipe.domain.models.Ingredient
import konsum.gandalf.mealmate.recipe.domain.models.Recipe
import konsum.gandalf.mealmate.recipe.domain.repository.IRecipeRepository
import konsum.gandalf.mealmate.user.domain.repository.IUserRepository
import konsum.gandalf.mealmate.utils.events.CustomEvent
import konsum.gandalf.mealmate.utils.repositories.images.IImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class RecipeEditViewModel
@Inject
constructor(
    private val userRepo: IUserRepository,
    private val imageRepo: IImageRepository,
    private val recipeRepo: IRecipeRepository
) : ViewModel() {

    private val eventsChannel = Channel<CustomEvent>()
    val eventFlow = eventsChannel.receiveAsFlow()

    private val _recipe = MutableLiveData<Recipe?>()
    val recipe
        get() = _recipe

    fun getOrCreateRecipe(recipe: Recipe?) =
        viewModelScope.launch {
            val inputRecipe = recipe ?: Recipe()
            _recipe.postValue(inputRecipe)

            if (inputRecipe.ingredients.isNotEmpty()) {
                _ingredients.postValue(inputRecipe.ingredients)
            } else {
                val emptyList = mutableListOf<Ingredient>()
                for (i in 1..13) {
                    emptyList.add(Ingredient())
                }
                _ingredients.postValue(emptyList)
            }
        }

    private val _areas = MutableLiveData<List<Area>?>()
    val currentAreas
        get() = _areas
    fun getAreas() =
        viewModelScope.launch {
            if (_areas.value == null) {
                val areas = recipeRepo.getAreas()
                _areas.postValue(areas)
            }
        }

    // initiated through the getOrCreateRecipe method
    private val _categories = MutableLiveData<List<Category>?>()
    val currentCategories
        get() = _categories

    fun getCategories() =
        viewModelScope.launch {
            if (_categories.value == null) {
                val categories = recipeRepo.getCategories()
                _categories.postValue(categories)
            }
        }

    private val _ingredients = MutableLiveData<List<Ingredient>?>()
    val currentIngredients
        get() = _ingredients

    fun validateCredentialInput(
        imageUri: Uri?,
        title: String,
        area: String,
        category: String,
        ingredients: List<Ingredient>,
        instructions: String,
        isCreateMode: Boolean
    ) =
        viewModelScope.launch {
            when {
                (imageUri == null && recipe.value?.imageUrl!!.isBlank()) -> {
                    eventsChannel.send(CustomEvent.Error("No Images was set!"))
                }
                title.isEmpty() -> {
                    eventsChannel.send(CustomEvent.ErrorCode(1))
                }
                area.isEmpty() -> {
                    eventsChannel.send(CustomEvent.ErrorCode(2))
                }
                category.isEmpty() -> {
                    eventsChannel.send(CustomEvent.ErrorCode(3))
                }
                instructions.isEmpty() -> {
                    eventsChannel.send(CustomEvent.ErrorCode(4))
                }
                else -> {
                    editUser(imageUri, title, area, category, instructions, ingredients, isCreateMode)
                }
            }
        }

    private fun editUser(
        uri: Uri?,
        title: String,
        area: String,
        category: String,
        instructions: String,
        ingredients: List<Ingredient>,
        isCreateMode: Boolean
    ) =
        viewModelScope.launch {
            val localUser = userRepo.getCurrentUser()
            localUser?.let { user ->
                _recipe.value?.let { recipe ->
                    withContext(Dispatchers.IO) {
                        uri?.let {
                            val urlOfUploadedImage = imageRepo.uploadImg(recipe.id, it)
                            if (urlOfUploadedImage != null) {
                                recipe.imageUrl = urlOfUploadedImage
                            }
                        }

                        recipe.title = title.trim()
                        recipe.area = area.trim()
                        recipe.category = category.trim()
                        recipe.ingredients = ingredients
                        recipe.instructions = instructions
                        recipe.owner = user

                        if (isCreateMode) {
                            recipeRepo.createRecipe(recipe)
                            eventsChannel.send(CustomEvent.Message("Created successfully"))
                        } else {
                            recipeRepo.updateRecipe(recipe)
                            eventsChannel.send(CustomEvent.Message("Updated successfully"))
                        }
                    }
                }
            }
        }

    fun deleteRecipe(recipeId: String) =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                recipeRepo.deleteRecipe(recipeId)
                eventsChannel.send(CustomEvent.Message("Recipe was deleted"))
            }
        }
}
