package konsum.gandalf.mealmate.ui.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.databinding.ActivityWelcomeScreenBinding
import konsum.gandalf.mealmate.ui.buttons.IntentButton
import konsum.gandalf.mealmate.ui.fragements.DecisionFragment

class WelcomeScreen : AppCompatActivity() {

  lateinit var binding: ActivityWelcomeScreenBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // binding = ActivityWelcomeScreenBinding.inflate(layoutInflater)
    setContentView(R.layout.activity_welcome_screen)

    val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
    val welcomeFragment =
        DecisionFragment(
            this@WelcomeScreen,
            IntentButton(LoginDeciderActivity::class.java,null),
            IntentButton(LoginDeciderActivity::class.java, null),
            null,
            null
		)
    fragmentTransaction.add(R.id.welcome_fragment, welcomeFragment).commit()
  }
}
