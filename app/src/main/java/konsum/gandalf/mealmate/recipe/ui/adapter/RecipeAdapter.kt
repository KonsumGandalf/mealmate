package konsum.gandalf.mealmate.recipe.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import konsum.gandalf.mealmate.databinding.CardRecipeViewBinding
import konsum.gandalf.mealmate.recipe.domain.models.Recipe

class RecipeAdapter(
    private var categorys: List<Recipe> = ArrayList<Recipe>()
) : RecyclerView.Adapter<RecipeAdapter.RecipeAdapterHolder>() {

    inner class RecipeAdapterHolder(val binding: CardRecipeViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeAdapterHolder {
        val binding = CardRecipeViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeAdapterHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeAdapterHolder, position: Int) {
        with(holder) {
            with(categorys[position]) {
                binding.recipeNameCv.text = title
                Picasso.get().load(imageUrl).into(binding.cardRecipeIv)
            }
        }
    }

    override fun getItemCount(): Int {
        return categorys.size
    }
}
