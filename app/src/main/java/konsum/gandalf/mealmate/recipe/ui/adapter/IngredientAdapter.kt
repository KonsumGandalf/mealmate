package konsum.gandalf.mealmate.recipe.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import konsum.gandalf.mealmate.databinding.CardSmallIngredientBinding
import konsum.gandalf.mealmate.recipe.domain.models.Ingredient

class IngredientAdapter(
    private var categorys: List<Ingredient> = ArrayList<Ingredient>()
) : RecyclerView.Adapter<IngredientAdapter.IngredientAdapterHolder>() {

    inner class IngredientAdapterHolder(val binding: CardSmallIngredientBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientAdapterHolder {
        val binding = CardSmallIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return IngredientAdapterHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientAdapterHolder, position: Int) {
        with(holder.binding) {
            categorys[position].apply {
                Picasso.get().load(imageUrl).into(
                    recipeDetailIv,
                    object : com.squareup.picasso.Callback {
                        override fun onSuccess() {
                            cardIngredientProgress.isVisible = false
                        }
                        override fun onError(e: java.lang.Exception?) {
                        }
                    }
                )
                cardSmallIngredientName.text = name
                cardSmallIngredientMeasure.text = measure
            }
        }
    }

    override fun getItemCount(): Int {
        return categorys.size
    }
}
