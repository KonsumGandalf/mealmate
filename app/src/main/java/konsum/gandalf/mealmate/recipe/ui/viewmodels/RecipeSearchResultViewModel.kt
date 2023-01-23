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
class RecipeSearchResultViewModel
@Inject
constructor(
    private val recipeRepository: IRecipeRepository
) : ViewModel() {

    private val _filteredRecipes = MutableLiveData<List<Recipe>?>()
    val currentFilteredRecipes
        get() = _filteredRecipes

    fun getFilteredRecipes(
        recipeName: String,
        selectedAreas: List<Area>,
        selectedCategories: List<Category>
    ) =
        viewModelScope.launch {
            val recipes = recipeRepository.filterRecipes(
                recipeName,
                selectedAreas,
                selectedCategories
            )
            _filteredRecipes.postValue(recipes)
        }
}
