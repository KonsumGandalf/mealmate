package konsum.gandalf.mealmate.user.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import konsum.gandalf.mealmate.databinding.CardRecipeOverviewBinding
import konsum.gandalf.mealmate.recipe.domain.models.Recipe
import konsum.gandalf.mealmate.user.ui.fragments.UserProfileFragmentDirections

class RecipeUserAdapter(
    private var categorys: List<Recipe> = ArrayList<Recipe>()
) : RecyclerView.Adapter<RecipeUserAdapter.RecipeAdapterHolder>() {

    inner class RecipeAdapterHolder(val binding: CardRecipeOverviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeAdapterHolder {
        val binding = CardRecipeOverviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeAdapterHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeAdapterHolder, position: Int) {
        with(holder) {
            with(categorys[position]) {
                binding.recipeNameCv.text = title
                Picasso.get().load(imageUrl).into(binding.cardRecipeIv)

                binding.root.setOnClickListener {
                    val action = UserProfileFragmentDirections.actionUserProfileFragmentToRecipeDetailFragment(this)
                    Navigation.findNavController(binding.root).navigate(action)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return categorys.size
    }
}
