package konsum.gandalf.mealmate.deprecated.views.login

import android.content.Context
import androidx.fragment.app.Fragment
import konsum.gandalf.mealmate.deprecated.views.buttons.IntentButton

data class DecisionFragmentInformation(
    val curContext: Context,
    val leftSign: IntentButton,
    val rightSign: IntentButton,
    val containerFrColor: Int?,
    val fragmentImage: Int?,
    val header: String?
)

class DecisionFragment(var builder: DecisionFragmentInformation) : Fragment() {
  /*// TODO: Rename and change types of parameters
  private var param1: String? = null
  private var param2: String? = null
  lateinit var binding: FragmentDecisionBinding

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
    binding = FragmentDecisionBinding.inflate(inflater, container, false)
    setInformation(builder)
    return binding.root
  }

  fun setInformation(builder: DecisionFragmentInformation) {
    with(builder) {
      fragmentImage?.let { binding.decisionFrIv.setImageResource(fragmentImage) }
      header?.let { binding.decisionFrHeader.text = header }
      containerFrColor?.let {
        binding.decisionFrContainer.backgroundTintList =
            getResources().getColorStateList(containerFrColor)
      }

      with(leftSign) {
        binding.decisionFrBtnLeft.setOnClickListener {
          if (header == DecisionFragmentsEnum.WELCOME.fragmentName) {
            reloadFragment(curContext, DecisionFragmentsEnum.LOGIN.fragmentName)
          } else {
            curContext.startActivity(Intent(curContext, nextActivity))
          }
        }
        if (header == DecisionFragmentsEnum.WELCOME.fragmentName) {
          binding.decisionFrBtnLeft.text = "L"
          binding.decisionFrBtnLeft.foreground = null
        } else {
          this?.sign?.let {
            binding.decisionFrBtnLeft.text = ""
            binding.decisionFrBtnLeft.foreground = resources.getDrawable(it)
          }
        }
      }

      with(rightSign) {
        binding.decisionFrBtnRight.setOnClickListener {
          if (header == DecisionFragmentsEnum.WELCOME.fragmentName) {
            reloadFragment(curContext, DecisionFragmentsEnum.REGISTER.fragmentName)
          } else {
            curContext.startActivity(Intent(curContext, nextActivity))
          }
        }
        if (header == DecisionFragmentsEnum.WELCOME.fragmentName) {
          binding.decisionFrBtnRight.text = "R"
          binding.decisionFrBtnRight.foreground = null
        } else {
          this?.sign?.let {
            binding.decisionFrBtnRight.text = ""
            binding.decisionFrBtnRight.foreground = resources.getDrawable(it)
          }
        }
      }
    }
  }

  fun reloadFragment(curContext: Context, mode: String) {
    lifecycleScope.launch {
      withContext(Dispatchers.Main) {
        ObjectAnimator.ofFloat(view, "alpha", 1f, 0f).apply {
          duration = 500
          start()
        }
        setInformation(DecisionFragmentBuilder(curContext, mode).information())
        ObjectAnimator.ofFloat(view, "alpha", 0f, 1f).apply {
          duration = 500
          start()
        }
      }
    }
  }*/
}
