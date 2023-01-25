package konsum.gandalf.mealmate.recipe.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.databinding.CardCategoryOverviewBinding
import konsum.gandalf.mealmate.recipe.domain.models.Category

class CategoryAdapter(
    private val context: Context,
    private var categorys: List<Category> = ArrayList<Category>(),
    private var selectedData: MutableLiveData<MutableList<Category>>
) : RecyclerView.Adapter<CategoryAdapter.CategoryAdapterHolder>() {

    inner class CategoryAdapterHolder(val binding: CardCategoryOverviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapterHolder {
        val binding = CardCategoryOverviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryAdapterHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryAdapterHolder, position: Int) {
        with(holder) {
            with(categorys[position]) {
                binding.categoryName.text = name
                Picasso.get().load(imageUrl).into(
                    binding.categoryIv,
                    object : com.squareup.picasso.Callback {
                        override fun onSuccess() {
                            binding.categoryProgess.isVisible = false
                        }
                        override fun onError(e: java.lang.Exception?) {
                        }
                    }
                )

                if (selectedData.value?.contains(this) == false) {
                    unhighlight(binding.categoryCard, binding.categoryName)
                } else {
                    highlight(binding.categoryCard, binding.categoryName)
                }
                binding.categoryCard.setOnClickListener {
                    Log.d("CategoryAdapter", selectedData.value.toString())
                    if (selectedData.value?.contains(this) == false) {
                        selectedData.value?.add(this)
                        highlight(binding.categoryCard, binding.categoryName)
                    } else {
                        selectedData.value?.remove(this)
                        unhighlight(binding.categoryCard, binding.categoryName)
                    }
                }
            }
        }
    }

    private fun highlight(card: CardView, text: TextView) {
        card.setCardBackgroundColor(context.getColor(R.color.yellow_highlighted))
        text.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    private fun unhighlight(card: CardView, text: TextView) {
        card.setCardBackgroundColor(context.getColor(R.color.yellow_soft))
        text.paintFlags = 0
    }

    override fun getItemCount(): Int {
        return categorys.size
    }
}
