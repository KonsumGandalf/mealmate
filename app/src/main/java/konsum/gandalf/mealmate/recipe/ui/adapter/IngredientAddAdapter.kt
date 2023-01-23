package konsum.gandalf.mealmate.recipe.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import konsum.gandalf.mealmate.databinding.CardIngredientAddBinding
import konsum.gandalf.mealmate.recipe.domain.models.Ingredient

class IngredientAddAdapter(
    private var ingredients: List<Ingredient>
) : RecyclerView.Adapter<IngredientAddAdapter.IngredientAdapterHolder>() {

    inner class IngredientAdapterHolder(val binding: CardIngredientAddBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientAdapterHolder {
        val binding = CardIngredientAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return IngredientAdapterHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientAdapterHolder, position: Int) {
        with(holder.binding) {
            ingredients[position].apply {
                recipeAddTitle.setText(name)
                recipeAddMeasure.setText(measure)

                recipeAddTitle.addTextChangedListener {
                    ingredients[position].name = it.toString()
                    ingredients[position].imageUrl = Ingredient.getImageUrl(it.toString())
                }
                recipeAddMeasure.addTextChangedListener {
                    ingredients[position].measure = it.toString()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    fun getIngredients(): List<Ingredient> {
        return ingredients.filter { it.name.isNotBlank() and it.measure.isNotBlank() }
    }
}
