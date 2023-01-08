package konsum.gandalf.mealmate.ui.fragements

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import konsum.gandalf.mealmate.databinding.FragmentDecisionBinding
import konsum.gandalf.mealmate.ui.buttons.IntentButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass. Use the [DecisionFragment.newInstance] factory method to create an
 * instance of this fragment.
 */
class DecisionFragment(
    private val curContext: Context,
    private val leftSign: IntentButton,
    private val rightSign: IntentButton,
    private val fragmentImage: Int?,
    private val header: String?
) : Fragment() {
  // TODO: Rename and change types of parameters
  private var param1: String? = null
  private var param2: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let {
      param1 = it.getString(ARG_PARAM1)
      param2 = it.getString(ARG_PARAM2)
    }
  }

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?,
  ): View? {

    val binding: FragmentDecisionBinding =
        FragmentDecisionBinding.inflate(inflater, container, false)
    fragmentImage?.let { binding.decisionFrIv.setImageResource(fragmentImage) }
    header?.let { binding.decisionFrHeader.text = header }

    with(leftSign) {
      binding.decisionFrBtnLeft.setOnClickListener {
        curContext.startActivity(Intent(curContext, nextActivity))
      }

      this?.sign?.let {
        binding.decisionFrBtnLeft.text = ""
        binding.decisionFrBtnLeft.foreground = resources.getDrawable(it)
      }
    }

    with(rightSign) {
      binding.decisionFrBtnRight.setOnClickListener {
        curContext.startActivity(Intent(curContext, nextActivity))
      }

      this?.sign?.let {
        binding.decisionFrBtnRight.text = ""
        binding.decisionFrBtnRight.foreground = resources.getDrawable(it)
      }
    }

    return binding.root
  }
}
