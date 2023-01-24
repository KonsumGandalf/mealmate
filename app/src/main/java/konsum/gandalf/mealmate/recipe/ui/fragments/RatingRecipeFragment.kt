package konsum.gandalf.mealmate.recipe.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.recipe.ui.viewmodels.RatingRecipeViewModel

class RatingRecipeFragment : Fragment() {

    companion object {
        fun newInstance() = RatingRecipeFragment()
    }

    private lateinit var viewModel: RatingRecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rating_recipe, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RatingRecipeViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
