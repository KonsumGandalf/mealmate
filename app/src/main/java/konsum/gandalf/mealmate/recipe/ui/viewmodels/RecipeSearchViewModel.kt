package konsum.gandalf.mealmate.recipe.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import konsum.gandalf.mealmate.recipe.domain.models.Area
import konsum.gandalf.mealmate.recipe.domain.models.Category
import konsum.gandalf.mealmate.recipe.domain.models.Recipe
import konsum.gandalf.mealmate.recipe.domain.repository.IRecipeRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeSearchViewModel
@Inject
constructor(
    private val recipeRepository: IRecipeRepository
) : ViewModel() {

    private val _areas = MutableLiveData<List<Area>?>()
    val currentAreas
        get() = _areas

    private val _selectedAreas = MutableLiveData<MutableList<Area>>()
    val currentSelectedAreas
        get() = _selectedAreas

    private val _categories = MutableLiveData<List<Category>?>()
    val currentCategories
        get() = _categories

    private val _selectedCategories = MutableLiveData<MutableList<Category>>()
    val currentSelectedCategories
        get() = _selectedCategories

    private val _recipes = MutableLiveData<List<Recipe>?>()
    val currentRandomRecipes
        get() = _recipes

    init {
        _selectedAreas.postValue(mutableListOf<Area>())
        _selectedCategories.postValue(mutableListOf<Category>())
        /**
         * The following could lead to performance improvements
         * this.getCategories()
         * this.getRandomRecipes()
         * this.getAreas()
         */
    }

    fun getCategories() =
        viewModelScope.launch {
            if (_categories.value == null) {
                val categories = recipeRepository.getCategories()
                _categories.postValue(categories)
            }
        }

    fun getRandomRecipes() =
        viewModelScope.launch {
            if (_recipes.value == null) {
                val randomRecipes = recipeRepository.getRandomRecipes()
                _recipes.postValue(randomRecipes)
            }
        }

    fun getAreas() =
        viewModelScope.launch {
            if (_areas.value == null) {
                val areas = recipeRepository.getAreas()
                _areas.postValue(areas)
            }
        }
}
