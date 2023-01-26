package konsum.gandalf.mealmate.evaluation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import konsum.gandalf.mealmate.databinding.FragmentHomeEvaluationBinding
import konsum.gandalf.mealmate.evaluation.ui.adapters.HomeEvaluationAdapter
import konsum.gandalf.mealmate.evaluation.ui.viewmodels.HomeEvaluationViewModel

@AndroidEntryPoint
class HomeEvaluationFragment : Fragment() {

    private lateinit var _binding: FragmentHomeEvaluationBinding
    private val binding
        get() = _binding

    private val viewModel: HomeEvaluationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeEvaluationBinding.inflate(inflater, container, false)

        registerEvaluations()

        return binding.root
    }

    private fun registerEvaluations() {
        viewModel.getEvaluations()
        viewModel.recipeEvaluations.observe(viewLifecycleOwner) { recipes ->
            recipes?.let {
                binding.homeEvaluationRv.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.homeEvaluationRv.adapter = HomeEvaluationAdapter(requireContext(), it)
            }
        }
    }
}
