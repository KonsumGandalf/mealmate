package konsum.gandalf.mealmate.evaluation.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.databinding.CardEvaluationViewBinding
import konsum.gandalf.mealmate.evaluation.domain.models.EvaluationPost

class EvaluationAdapter(
    private val context: Context,
    private var eval: List<EvaluationPost> = ArrayList<EvaluationPost>()
) : RecyclerView.Adapter<EvaluationAdapter.EvalAdapterHolder>() {

    inner class EvalAdapterHolder(val binding: CardEvaluationViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvalAdapterHolder {
        val binding = CardEvaluationViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EvalAdapterHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: EvalAdapterHolder, position: Int) {
        with(holder.binding) {
            eval[position].apply {
                Picasso.get().load(owner?.imageUrl).into(
                    recipeEvaluationCardIv,
                    object : com.squareup.picasso.Callback {
                        override fun onSuccess() {
                            recipeEvaluationCardProgress.isVisible = false
                        }
                        override fun onError(e: java.lang.Exception?) {
                        }
                    }
                )
                owner?.username?.let {
                    recipeEvaluationCardUsername.text = it
                }
                recipeEvaluationCardRating.text = "$rating"
                rating?.let {
                    recipeEvaluationCardRating.setTextColor(getColor(it))
                }

                recipeEvaluationCardDifficulty.text = "$difficulty"
                difficulty?.let {
                    recipeEvaluationCardDifficulty.setTextColor(getColor(it))
                }

                recipeEvaluationCardCreated.text = createdAt
                recipeEvaluationCardComment.text = comment
                recipeEvaluationCardCreatedLabel.text = "Created"
                recipeEvaluationCardDifficultyLabel.text = "Difficulty"
                recipeEvaluationCardRatingLabel.text = "Rating"
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
