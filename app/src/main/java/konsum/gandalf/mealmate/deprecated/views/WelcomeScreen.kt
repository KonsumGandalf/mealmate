package konsum.gandalf.mealmate.deprecated.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.databinding.ActivityWelcomeScreenBinding
import konsum.gandalf.mealmate.authentication.ui.constants.DecisionFragmentBuilder
import konsum.gandalf.mealmate.deprecated.views.enums.DecisionFragmentsEnum
import konsum.gandalf.mealmate.deprecated.views.login.DecisionFragment

class WelcomeScreen : AppCompatActivity() {

  lateinit var binding: ActivityWelcomeScreenBinding
  lateinit var fragmentTransaction: FragmentTransaction
  lateinit var welcomeFragment: DecisionFragment

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // binding = ActivityWelcomeScreenBinding.inflate(layoutInflater)
    setContentView(R.layout.activity_welcome_screen)

    fragmentTransaction = supportFragmentManager.beginTransaction()
    welcomeFragment =  DecisionFragmentBuilder(this,
		DecisionFragmentsEnum.WELCOME.fragmentName).build()
    fragmentTransaction.add(R.id.welcome_fragment, welcomeFragment).commit()

  }

  override fun onBackPressed() {
    //welcomeFragment.reloadFragment(this, DecisionFragmentsEnum.WELCOME.fragmentName)
  }
}
