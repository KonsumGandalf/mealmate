package konsum.gandalf.mealmate.deprecated.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.databinding.ActivityLoginBinding
import konsum.gandalf.mealmate.authentication.ui.fragments.LoginDecisionFragment

class LoginActivity : AppCompatActivity() {
	lateinit var binding: ActivityLoginBinding;
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityLoginBinding.inflate(layoutInflater)
		setContentView(binding.root)
	}
}