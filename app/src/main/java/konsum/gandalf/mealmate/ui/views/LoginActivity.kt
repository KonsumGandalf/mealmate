package konsum.gandalf.mealmate.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import konsum.gandalf.mealmate.R
import konsum.gandalf.mealmate.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
	lateinit var binding: ActivityLoginBinding;
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityLoginBinding.inflate(layoutInflater)
		setContentView(binding.root)
	}
}