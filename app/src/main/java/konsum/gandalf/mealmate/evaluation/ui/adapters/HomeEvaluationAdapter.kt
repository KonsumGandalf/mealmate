package konsum.gandalf.mealmate.evaluation.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.databinding.CardEvaluationViewHomeBinding
import konsum.gandalf.mealmate.evaluation.domain.models.EvaluationPost
import konsum.gandalf.mealmate.evaluation.ui.fragments.HomeEvaluationFragmentDirections

class HomeEvaluationAdapter(
    private val context: Context,
    private var eval: List<EvaluationPost> = ArrayList<EvaluationPost>()
) : RecyclerView.Adapter<HomeEvaluationAdapter.EvalAdapterHolder>() {

    inner class EvalAdapterHolder(val binding: CardEvaluationViewHomeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvalAdapterHolder {
        val binding = CardEvaluationViewHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EvalAdapterHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: EvalAdapterHolder, position: Int) {
        with(holder.binding) {
            eval[position].apply {
                Picasso.get().load(recipe?.imageUrl).into(
                    cardEvaluationIv,
                    object : com.squareup.picasso.Callback {
                        override fun onSuccess() {
                            recipeEvaluationCardProgress.isVisible = false
                        }
                        override fun onError(e: java.lang.Exception?) {
                        }
                    }
                )
                owner?.let {
                    recipeNameCv.text = it.username
                    Picasso.get().load(it.imageUrl).into(
                        recipeEvaluationCardIv,
                        object : com.squareup.picasso.Callback {
                            override fun onSuccess() {
                                recipeEvaluationCardProgress.isVisible = false
                            }
                            override fun onError(e: java.lang.Exception?) {
                            }
                        }
                    )
                }

                evaluationRating.text = "$rating"
                rating?.let {
                    evaluationRating.setTextColor(getColor(it))
                }

                evaluationDifficulty.text = "$difficulty"
                difficulty?.let {
                    evaluationDifficulty.setTextColor(getColor(5f - it))
                }

                root.setOnClickListener {
                    val action = HomeEvaluationFragmentDirections.actionHomeEvaluationFragmentToRecipeEvaluationFragment(
                        recipe!!
                    )
                    Navigation.findNavController(root).navigate(action)
                }
            }
        }
    }

    private fun getColor(value: Float): Int {
        return when {
            value >= 4 -> {
                context.getColor(R.color.green_500)
            }
            value >= 2.5 -> {
                context.getColor(R.color.black_soft)
            }
            else -> {
                context.getColor(R.color.red_500)
            }
        }
    }

    override fun getItemCount(): Int {
        return eval.size
    }
}
