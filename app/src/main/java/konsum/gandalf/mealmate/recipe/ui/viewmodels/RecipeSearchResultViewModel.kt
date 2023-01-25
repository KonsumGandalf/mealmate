package konsum.gandalf.mealmate.recipe.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import konsum.gandalf.mealmate.evaluation.domain.repository.IEvaluationRepository
import konsum.gandalf.mealmate.recipe.domain.models.Area
import konsum.gandalf.mealmate.recipe.domain.models.Category
import konsum.gandalf.mealmate.recipe.domain.models.Recipe
import konsum.gandalf.mealmate.recipe.domain.repository.IRecipeRepository
import konsum.gandalf.mealmate.utils.helper.Helper
import konsum.gandalf.mealmate.utils.models.DifRat
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeSearchResultViewModel
@Inject
constructor(
    private val recipeRepository: IRecipeRepository,
    private val evalRepo: IEvaluationRepository
) : ViewModel() {

    private val _filteredRecipes = MutableLiveData<List<Recipe>?>()
    val currentFilteredRecipes
        get() = _filteredRecipes

    private val _evaluations = MutableLiveData<List<DifRat>>()
    val filteredRecipesEvaluations
        get() = _evaluations

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

            val fullList = ArrayList<DifRat>()
            for (recipe in recipes) {
                val result = Helper.calculateRatingAndDifficulty(evalRepo.getEvaluations(recipe.recipeId))
                fullList.add(result)
            }
            _evaluations.postValue(fullList)
        }
}
