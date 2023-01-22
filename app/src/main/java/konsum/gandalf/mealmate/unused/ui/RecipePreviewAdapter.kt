package konsum.gandalf.mealmate.unused.ui

import konsum.gandalf.mealmate.recipe.domain.models.Recipe

class RecipePreviewAdapter(
    private var recipes: List<Recipe> = ArrayList<Recipe>()
) /*: RecyclerView.Adapter<RecipePreviewAdapter.IngredientAdapterHolder>() {

    inner class IngredientAdapterHolder(val binding: CardRecipeSearchPreviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientAdapterHolder {
        val binding = CardRecipeSearchPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return IngredientAdapterHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientAdapterHolder, position: Int) {
        with(holder.binding) {
            recipes[position].apply {
                Picasso.get().load(imageUrl).into(
                    recipePreviewIv,
                    object : com.squareup.picasso.Callback {
                        override fun onSuccess() {
                            recipePreviewProgress.isVisible = false
                        }
                        override fun onError(e: java.lang.Exception?) {
                        }
                    }
                )
                recipePreviewTitle.text = title

                root.setOnClickListener {
                    val action = RecipeSearchFragmentDirections.actionRecipeSearchFragmentToRecipeDetailFragment2(this)
                    Navigation.findNavController(root).navigate(action)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return recipes.size
    }
}*/
